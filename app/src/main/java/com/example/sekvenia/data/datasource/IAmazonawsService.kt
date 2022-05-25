package com.example.sekvenia.data.datasource

import com.example.sekvenia.data.entity.ApiFilms
import com.example.sekvenia.data.remote.network.INetwork
import retrofit2.http.GET

fun provideAmazonawsService(network: INetwork): IAmazonawsService =
    network.retrofit.create(
        IAmazonawsService::class.java
    )

interface IAmazonawsService {
    @GET("sequeniatesttask/films.json")
    suspend fun getFilms(): ApiFilms
}