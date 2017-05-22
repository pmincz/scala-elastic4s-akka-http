package com.demo.model

import java.util.Date

import com.demo.model.util.BaseEntity
import com.fasterxml.jackson.annotation.{JsonFormat, JsonProperty}

case class Movie(id: Long, adult: Boolean, overview: String, @JsonProperty("original_title") originalTitle: String,
                 title: String, popularity: Double, @JsonProperty("vote_count") voteCount: Int,
                 @JsonProperty("vote_average") voteAverage: Double,
                 @JsonProperty("release_date")
                 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
                 releaseDate: Date) extends BaseEntity[Long]
