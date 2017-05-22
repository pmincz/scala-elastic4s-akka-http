package com.demo.model.util

import akka.http.scaladsl.server.Rejection
import com.fasterxml.jackson.annotation.JsonProperty

case class Error(statusCode: Int, reason: String) extends Throwable with Rejection

case class ErrorResponse(@JsonProperty("status_code") statusCode: Int, reason: String)
