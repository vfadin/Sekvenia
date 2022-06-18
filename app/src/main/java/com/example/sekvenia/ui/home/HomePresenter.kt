//package com.example.sekvenia.ui.home
//
//import com.example.sekvenia.domain.entity.Film
//import com.example.sekvenia.domain.repo.IHomeRepo
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.filterNotNull
//import kotlinx.coroutines.launch
//import moxy.InjectViewState
//import moxy.MvpPresenter
//import moxy.presenterScope
//
//@InjectViewState
//class HomePresenter(
//    private val homeRepo: IHomeRepo
//) : MvpPresenter<IHomeView>() {
//
//    private val TITLES_BEFORE_GENRES = 1
//    private val _requestState = MutableStateFlow<Byte?>(null)
//    val requestStateFlow = _requestState.asStateFlow().filterNotNull()
//
//    private val filmList = mutableListOf<Film>()
//    val genreSet = mutableSetOf<String>()
//    var filteredFilmList = mutableListOf<Film>()
//    var genreSelected: Int = -1
//
//    init {
//        viewState.bindUi()
//        getFilms()
//    }
//
//    fun getFilteredFilmList(position: Int): MutableList<Film> {
//        genreSelected = position
//        filteredFilmList.clear()
//        filmList.map { film ->
//            if (film.genres.contains(genreSet.elementAtOrNull(position - TITLES_BEFORE_GENRES))) {
//                filteredFilmList.add(film)
//            }
//        }
//        return filteredFilmList
//    }
//
//    fun getFilms() {
//        presenterScope.launch {
//            filmList.clear()
//            filteredFilmList.clear()
//            val result = homeRepo.getFilms()?.sortedBy {
//                it.localized_name
//            }
//            result?.map { film ->
//                filmList.add(film)
//                filteredFilmList.add(film)
//                film.genres.map {
//                    genreSet.add(it)
//                }
//            }
//            _requestState.value = null
//            _requestState.emit(1)
//        }
//    }
//}