package com.demo.client

import com.demo.client.model.MovieClientResponse
import com.demo.client.util.RestClient
import com.demo.model.Movie

object MovieClient extends RestClient[MovieClientResponse, Movie] {

}
