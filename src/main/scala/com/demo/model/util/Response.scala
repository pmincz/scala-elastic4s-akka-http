package com.demo.model.util

case class Response[T](items: List[T], paging: PageInfo)

case class PageInfo(offset: Int = 0, limit: Int = 10, total: Long = 0L)
