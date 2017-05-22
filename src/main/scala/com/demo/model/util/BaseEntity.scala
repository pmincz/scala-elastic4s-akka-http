package com.demo.model.util

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
trait BaseEntity[K] {
  val id: K
}
