package com.example.sekvenia.data.datasource

import com.example.sekvenia.data.network.safeApiCall


class AmazonawsRemoteDataSource(
    private val api: IAmazonawsService
) {
    suspend fun getFilms() = safeApiCall {
        api.getFilms()
    }
}