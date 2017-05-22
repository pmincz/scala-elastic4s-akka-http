package com.demo.client.model

import com.demo.client.util.ClientResponse
import com.demo.model.Movie
import com.fasterxml.jackson.annotation.JsonProperty

case class MovieClientResponse(results: List[Movie], @JsonProperty("total_pages") totalPages: Int) extends ClientResponse[Movie]
