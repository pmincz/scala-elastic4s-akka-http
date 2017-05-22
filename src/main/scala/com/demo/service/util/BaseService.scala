package com.demo.service.util

import com.demo.model.util.{BaseEntity, Response, SortPageInfo}
import com.demo.repository.util.BaseRepository

import scala.concurrent.Future

abstract class BaseService[T <: BaseEntity[K] : Manifest, K](repository: BaseRepository[T, K]) {

  def createIndex = {
    repository.createIndices
  }

  def index(entity: T) = {
    repository.index(entity)
  }

  def indexBulk(entities: List[T]) = {
    repository.indexBulk(entities)
  }

  def find(field: String, q: String, paging: SortPageInfo): Future[Response[T]] = {
    repository.find(field, q, paging)
  }

}
