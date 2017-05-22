package com.demo.provider

import com.typesafe.config._

object ConfigProvider {

  val env = System.getProperty("ENV", "develop")

  val conf = ConfigFactory.load()

  def apply() = conf.hasPath(env) match {
    case true => conf.getConfig(env).withFallback(conf)
    case false => conf
  }

}
