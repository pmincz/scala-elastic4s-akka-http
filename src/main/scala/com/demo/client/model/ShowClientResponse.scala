package com.demo.client.model

import com.demo.client.util.ClientResponse
import com.demo.model.Show
import com.fasterxml.jackson.annotation.JsonProperty

case class ShowClientResponse(results: List[Show], @JsonProperty("total_pages") totalPages: Int) extends ClientResponse[Show]
