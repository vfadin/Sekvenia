package com.example.sekvenia.ui.home

import com.example.sekvenia.domain.repo.IHomeRepo
import moxy.MvpPresenter

class HomePresenter(
    private val homeRepo: IHomeRepo
): MvpPresenter<IHomeView>() {
    suspend fun getFilms() = homeRepo.getFilms()
}