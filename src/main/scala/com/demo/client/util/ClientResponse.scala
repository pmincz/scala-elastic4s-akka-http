package com.demo.client.util

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
trait ClientResponse[T] {
  val results: List[T]
  @JsonProperty("total_pages")
  val totalPages: Int
}
