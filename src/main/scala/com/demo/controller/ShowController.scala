package com.demo.controller

import com.demo.controller.util.BaseController
import com.demo.model.Show
import com.demo.service.ShowService

object ShowController extends BaseController[Show, Long](ShowService) {

  val routeName = "shows"

  override def getRoute = getRoutes

}
