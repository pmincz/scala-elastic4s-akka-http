package com.demo.client

import com.demo.client.model.ShowClientResponse
import com.demo.client.util.RestClient
import com.demo.model.Show

object ShowClient extends RestClient[ShowClientResponse, Show] {

}
