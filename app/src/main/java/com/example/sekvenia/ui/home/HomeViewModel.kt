package com.example.sekvenia.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sekvenia.R
import com.example.sekvenia.domain.entity.*
import com.example.sekvenia.domain.repo.IHomeRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeRepo: IHomeRepo
) : ViewModel() {

    private val _filmsState = MutableStateFlow<List<HomeRecyclerViewItem>?>(null)
    val filmsStateFlow = _filmsState.asStateFlow().filterNotNull()

    private val _detailedFilmState = MutableStateFlow<List<Film>?>(null)
    val detailedFilmStateFlow = _detailedFilmState.asStateFlow().filterNotNull()

    private val filmList = mutableListOf<Film>()
    val genreSet = mutableSetOf<String>()
    var genreSelected: Int = -1

    init {
        getFilms()
    }

    fun getFilms() {
        viewModelScope.launch {
            genreSet.clear()
            homeRepo.getFilms()?.sortedBy {
                it.localized_name
            }?.map { film ->
                filmList.add(film)
                film.genres.map {
                    genreSet.add(it)
                }
            }
            setDataList(filmList)
            _detailedFilmState.value = filmList
        }
    }

    private fun setDataList(list: List<Film>) {
        val newDataList = mutableListOf<HomeRecyclerViewItem>()
        with(newDataList) {
            add("Жанры".toHomeRecyclerViewItemTitle())
            addAll(genreSet.map {
                it.toHomeRecyclerViewItemGenre()
            })
            add("Фильмы".toHomeRecyclerViewItemTitle())
            addAll(list.map {
                it.toHomeRecyclerViewItemFilm()
            })
        }
        _filmsState.value = newDataList
    }

    fun getFilteredFilmList(position: Int) {
        genreSelected = position
        var result = mutableListOf<Film>()
        filmList.map { film ->
            if (film.genres.contains(genreSet.elementAtOrNull(position - 1))) {
                result.add(film)
            }
        }
        setDataList(result)
    }
}