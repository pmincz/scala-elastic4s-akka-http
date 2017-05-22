package com.demo.controller

import akka.http.scaladsl.server.{Directives, Route}
import com.demo.client.{MovieClient, ShowClient}
import com.demo.provider.ActorSystemProvider
import com.demo.service.{MovieService, ShowService}
import de.heikoseeberger.akkahttpjackson.JacksonSupport

import scala.concurrent.{ExecutionContext, Future}

object IndexController extends JacksonSupport with Directives {

  implicit val ec: ExecutionContext = ActorSystemProvider.executionContext
  implicit val materializer = ActorSystemProvider.materializer

  def getRoute: Route = {
    post {
      path("index") {
        complete {
          MovieService.createIndex
          complete(200)
        }
      }
    }
  } ~
  get {
    path("refresh" / Segment) { docType =>
      complete {
        Future {
          docType match {
            case "shows" => {
              ShowClient.getItems("https://api.themoviedb.org/3/discover/tv?sort_by=popularity.desc&api_key=6419e9a087dff7e51d64798317eed191").map { shows =>
                ShowService.indexBulk(shows)
              }
            }
            case "movies" => {
              MovieClient.getItems("https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=6419e9a087dff7e51d64798317eed191").map { movies =>
                val x = movies
                println(x.size)
                MovieService.indexBulk(movies)
              }
            }
          }

          complete(200)
        }
      }
    }
  }


}
