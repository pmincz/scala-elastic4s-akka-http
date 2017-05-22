package com.demo.model

import java.util.Date

import com.demo.model.util.BaseEntity
import com.fasterxml.jackson.annotation.{JsonFormat, JsonProperty}

case class Show(id: Long, popularity: Double, @JsonProperty("vote_average") voteAverage: Double, overview: String,
                @JsonProperty("vote_count") voteCount: Int, name: String,
                @JsonProperty("original_name") originalName: String,
                @JsonProperty("first_air_date")
                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
                firstAirDate: Date) extends BaseEntity[Long]