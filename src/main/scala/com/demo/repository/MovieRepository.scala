package com.demo.repository

import com.demo.model.Movie
import com.demo.repository.util.BaseRepository

object MovieRepository extends BaseRepository[Movie, Long]("movies"){

}
