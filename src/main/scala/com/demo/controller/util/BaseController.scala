package com.demo.controller.util

import akka.http.scaladsl.server.{Directives, Route}
import com.demo.model.util.{BaseEntity, SortPageInfo}
import com.demo.provider.ActorSystemProvider
import com.demo.service.util.BaseService
import de.heikoseeberger.akkahttpjackson.JacksonSupport

import scala.concurrent.ExecutionContext

abstract class BaseController[T <: BaseEntity[K] : Manifest, K](service: BaseService[T, K]) extends Directives with JacksonSupport {

  val pagingParams = parameters('offset.as[Int] ? 0, 'limit.as[Int] ? 10, 'order_by ? "").as(SortPageInfo)
  val urlParams = parameterSeq

  implicit val ec: ExecutionContext = ActorSystemProvider.executionContext
  implicit val materializer = ActorSystemProvider.materializer

  def getRoute: Route

  val routeName: String

  def getRoutes: Route = {
    get {
      path(routeName) {
        urlParams { params =>
          pagingParams { sort =>
            parameters('q.as[String] ? "", 'field.as[String] ? "") { (query, field) =>
              complete {
                service.find(field, query, sort)
              }
            }
          }
        }
      }
    }
  }

}
