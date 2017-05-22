package com.demo.controller

import com.demo.controller.util.BaseController
import com.demo.model.Movie
import com.demo.service.MovieService

object MovieController extends BaseController[Movie, Long](MovieService) {

  val routeName = "movies"

  override def getRoute = getRoutes

}
