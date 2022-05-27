package com.example.sekvenia.domain.entity

import com.example.sekvenia.ui.home.HomeRecyclerViewHolder

sealed class HomeRecyclerViewItem {

    class Title(
        val text: String
    ) : HomeRecyclerViewItem()

    class Film(
        val title: String,
        val imageUrl: String
    ) : HomeRecyclerViewItem()

    class Genre(
        val title: String,
        var isPressed: Boolean = false
    ) : HomeRecyclerViewItem()

}


fun Film.toHomeRecyclerViewItemFilm() = HomeRecyclerViewItem.Film(
    title = this.localized_name,
    imageUrl = this.image_url
)

fun String.toHomeRecyclerViewItemTitle() = HomeRecyclerViewItem.Title(
    text = this
)

fun String.toHomeRecyclerViewItemGenre() = HomeRecyclerViewItem.Genre(
    title = this,
    isPressed = false
)