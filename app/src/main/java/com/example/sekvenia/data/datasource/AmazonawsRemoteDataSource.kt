package com.example.sekvenia.data.datasource

import com.example.sekvenia.data.entity.ApiFilm
import com.example.sekvenia.data.entity.ApiFilms

class AmazonawsRemoteDataSource(
    private val api: IAmazonawsService
) {
    suspend fun getFilms() : ApiFilms {
        return api.getFilms()
    }
}