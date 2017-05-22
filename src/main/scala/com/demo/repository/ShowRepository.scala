package com.demo.repository

import com.demo.model.Show
import com.demo.repository.util.BaseRepository

object ShowRepository extends BaseRepository[Show, Long]("shows"){

}
