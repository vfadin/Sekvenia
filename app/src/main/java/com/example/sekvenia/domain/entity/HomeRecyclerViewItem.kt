package com.example.sekvenia.domain.entity

sealed class HomeRecyclerViewItem {

    class ItemTitle(
        val text: String
    ) : HomeRecyclerViewItem()

    class ItemFilm(
        val title: String,
        val imageUrl: String
    ) : HomeRecyclerViewItem()

    class ItemGenre(
        val title: String,
    ) : HomeRecyclerViewItem()

}


fun Film.toHomeRecyclerViewItemFilm() = HomeRecyclerViewItem.ItemFilm(
    title = this.localized_name,
    imageUrl = this.image_url
)

fun String.toHomeRecyclerViewItemTitle() = HomeRecyclerViewItem.ItemTitle(
    text = this
)

fun String.toHomeRecyclerViewItemGenre() = HomeRecyclerViewItem.ItemGenre(
    title = this
)