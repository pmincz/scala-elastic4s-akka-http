package com.demo.client.util

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.demo.provider.ActorSystemProvider
import de.heikoseeberger.akkahttpjackson.JacksonSupport

import scala.concurrent.Future

class RestClient[T <: ClientResponse[R] : Manifest, R] extends ActorSystemProvider with JacksonSupport {

  implicit val unmarshaller = JacksonSupport.jacksonUnmarshaller[T]
  val connectionFlow: Flow[HttpRequest, HttpResponse, Future[Http.OutgoingConnection]] = Http().outgoingConnectionHttps("api.themoviedb.org")

  def getItems(uri: String): Future[List[R]] = {
    Source.single(HttpRequest(uri = uri))
      .via(connectionFlow)
      .runWith(Sink.last)
      .flatMap(response => {
        Unmarshal(response.entity).to[T].map(_.results)
    })
  }

}
