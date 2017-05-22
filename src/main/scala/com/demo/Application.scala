package com.demo

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, ExceptionHandler}
import com.demo.controller.{IndexController, MovieController, ShowController}
import com.demo.model.util.{Error, ErrorResponse}
import com.demo.provider.{ActorSystemProvider, ConfigProvider}
import de.heikoseeberger.akkahttpjackson.JacksonSupport

import scala.util.Try

object Application extends App with ActorSystemProvider with Directives with JacksonSupport {

  val config = ConfigProvider()

  val host = Try(config.getString("service.host")).getOrElse("0.0.0.0")
  val port = Try(config.getInt("service.port")).getOrElse(5000)

  implicit def myExceptionHandler: ExceptionHandler =
    ExceptionHandler {
      case e: Error =>
        extractUri { uri =>
          complete(StatusCodes.getForKey(e.statusCode).get, ErrorResponse(e.statusCode, e.reason))
        }
      case e: Exception =>
        extractUri { uri =>
          complete(StatusCodes.InternalServerError.intValue, ErrorResponse(StatusCodes.InternalServerError.intValue, e.getMessage))
        }
    }

  val route = handleExceptions(myExceptionHandler) { ShowController.getRoute ~ MovieController.getRoute ~ IndexController.getRoute }

  Http().bindAndHandle(route, host, port)

}

