package com.demo.provider

import com.sksamuel.elastic4s.{ElasticClient, ElasticsearchClientUri}
import org.elasticsearch.common.settings.Settings

import scala.util.Try

object ElasticSearchProvider {

  private val config = ConfigProvider()
  private val host = Try(config.getString("elastic.host")).getOrElse("localhost")
  private val port = Try(config.getInt("elastic.port")).getOrElse(9300)
  private val clusterName = Try(config.getString("elastic.cluster")).getOrElse("elastic-dev")

  private val settings = Settings.builder().put("cluster.name", clusterName).build()

  lazy val client = {
    println("preparing to create indices with settings: "+settings.getAsMap)
    println("elasticsearch host: "+host+ " - cluster: "+ clusterName+" - port: "+port)
    ElasticClient.transport(settings, ElasticsearchClientUri(host, port))
  }
}
