package com.example.sekvenia.data.repo

import com.example.sekvenia.data.datasource.AmazonawsRemoteDataSource
import com.example.sekvenia.data.entity.toListOfFilm
import com.example.sekvenia.domain.entity.Film
import com.example.sekvenia.domain.repo.IHomeRepo

class HomeRepo(
    private val dataSource: AmazonawsRemoteDataSource
) : IHomeRepo {
    override suspend fun getFilms() : List<Film>? {
        return dataSource.getFilms().let {
            it?.toListOfFilm()
        }
    }
}