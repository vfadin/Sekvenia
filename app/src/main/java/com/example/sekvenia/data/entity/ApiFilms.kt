package com.example.sekvenia.data.entity

import com.example.sekvenia.domain.entity.Film
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiFilms(
    @Json(name = "films")
    val films: List<ApiFilm>
)

fun ApiFilms.toListOfFilm() : List<Film> {
    return this.films.map {
        it.toFilm()
    }
}