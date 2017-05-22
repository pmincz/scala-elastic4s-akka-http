package com.demo.repository.util

import java.util.{Calendar, Date}

import akka.http.scaladsl.model.{HttpEntity, MediaTypes}
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.demo.model.util.{BaseEntity, PageInfo, Response, SortPageInfo}
import com.demo.provider.{ActorSystemProvider, ConfigProvider, ElasticSearchProvider}
import com.sksamuel.elastic4s.ElasticDsl.{fieldSort, search, termQuery, _}
import com.sksamuel.elastic4s.jackson.ElasticJackson.Implicits._
import com.typesafe.scalalogging.Logger
import de.heikoseeberger.akkahttpjackson.JacksonSupport

import scala.concurrent.Future
import scala.io.Source

abstract class BaseRepository[T <: BaseEntity[K] : Manifest, K](typeName: String) extends JacksonSupport with ActorSystemProvider {

  val logger = Logger(getClass.getName)
  val config = ConfigProvider()

  val client = ElasticSearchProvider.client
  implicit val unmarshaller = JacksonSupport.jacksonUnmarshaller[T]

  def createIndices: Unit = {
    val json = Source.fromResource("elastic/mapping").getLines.mkString

    val indexName = getIndexName
    client.execute {
      createIndex(indexName) source json
    }
  }

  def getIndexName = {
    config.getString("elastic.index.name")
  }

  def index(entity: T) = {
    client.execute {
        indexInto(getIndexName / typeName) id entity.id.toString doc entity
    }.recover( { case e: Exception => logger.error("error in index: "+ e.getMessage)})
  }

  def indexBulk(entities: List[T]) = {
    logger.info("indexBulk size: "+entities.size)
    val bulkIndex = entities.map(entity => indexInto(getIndexName / typeName) id entity.id.toString doc entity)
    client.execute {
      bulk(
        bulkIndex
      )
    }.recover( { case e: Exception => logger.error("error in indexBulk: "+ e.getMessage)})
  }

  def find(field: String, q: String, paging: SortPageInfo): Future[Response[T]] = {
    var total = 0L
    client.execute {
      val searchDefinition = search(getIndexName / typeName)
      val searchBuilder = q match {
        case query if query.isEmpty => searchDefinition matchAll()
        case query if !query.isEmpty && !field.isEmpty => {
          val fields = field.split(",") toList
          val values = query.split(",") toList

          val queries = (fields, values).zipped.map { (f, v) => termQuery(f, v) }

          searchDefinition query boolQuery().must(queries)
        }
        case _ => searchDefinition query(q)
      }

      paging.sort.isEmpty match {
        case false => searchBuilder sortBy fieldSort(paging.sort) size paging.limit from paging.offset
        case true => searchBuilder size paging.limit from paging.offset
      }
    }.flatMap(response => {
      total = response.totalHits
      val entities = response.hits.map(hit => {
        val entity = HttpEntity(MediaTypes.`application/json`, hit.sourceAsString)
        Unmarshal(entity).to[T]
      })
      Future.sequence(entities toList)
    }).map(f => Response[T](f, PageInfo(paging.offset, paging.limit, total)))
  }

  def deleteByDate(field: String, fromUnits: Int, untilUnits: Int, calendarField: Int) = {
    val indexes = getIndexName

    val untilDate = getDate(calendarField, untilUnits)
    val fromDate = getDate(calendarField, fromUnits)

    client.execute {
        deleteIn(indexes).by(rangeQuery(field) from fromDate to untilDate)
    }.recover( { case e: Exception => logger.error("error in delete by query: "+ e.getMessage)})
  }

  private def getDate(calendarField: Int, units: Int) = {
    val now = new Date()
    val cal = Calendar.getInstance()
    cal.setTime(now)
    cal.add(calendarField, units)
    cal.getTime
  }

}
