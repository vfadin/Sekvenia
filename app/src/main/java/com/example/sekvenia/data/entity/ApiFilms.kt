package com.example.sekvenia.data.entity

import com.example.sekvenia.domain.entity.Film
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiFilms(
    @Json(name = "films")
    val films: List<ApiFilm>
)

@JsonClass(generateAdapter = true)
data class ApiFilm(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "localized_name")
    val localized_name: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "year")
    val year: Int?,
    @Json(name = "rating")
    val rating: Double?,
    @Json(name = "image_url")
    val image_url: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "genres")
    val genres: List<String>?
)

fun ApiFilm.toFilm() = Film(
    id = this.id ?: -1,
    localized_name = this.localized_name ?: "",
    name = this.name ?: "",
    year = this.year ?: -1,
    rating = this.rating ?: 0.0,
    image_url = this.image_url ?: "",
    description = this.description ?: "",
    genres = this.genres ?: listOf()
)

fun ApiFilms.toListOfFilm(): List<Film> {
    return this.films.map {
        it.toFilm()
    }
}