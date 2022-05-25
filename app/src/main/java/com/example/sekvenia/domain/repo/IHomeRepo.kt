package com.example.sekvenia.domain.repo

import com.example.sekvenia.domain.entity.Film

interface IHomeRepo {
    suspend fun getFilms(): List<Film>
}