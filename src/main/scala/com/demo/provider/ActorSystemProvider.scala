package com.demo.provider

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

trait ActorSystemProvider {

  implicit val system = ActorSystem("croupier")
  implicit val executionContext = system.dispatcher
  implicit val materializer = ActorMaterializer()

}

object ActorSystemProvider extends ActorSystemProvider
