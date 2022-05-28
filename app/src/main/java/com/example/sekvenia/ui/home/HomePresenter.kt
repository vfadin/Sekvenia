package com.example.sekvenia.ui.home

import com.example.sekvenia.domain.entity.Film
import com.example.sekvenia.domain.repo.IHomeRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope

class HomePresenter(
    private val homeRepo: IHomeRepo
) : MvpPresenter<IHomeView>() {

    private val _filmState = MutableStateFlow<List<Film>?>(null)
    val filmStateFlow = _filmState.asStateFlow().filterNotNull()

    init {
        getFilms()
    }
    fun getFilms() {
        presenterScope.launch {
            _filmState.emit(
                homeRepo.getFilms().sortedBy {
                    it.localized_name
                })
        }
    }
}