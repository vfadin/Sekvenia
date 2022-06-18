package com.example.sekvenia.domain.entity

sealed class HomeRecyclerViewItem {

    data class ItemTitle(
        val text: String
    ) : HomeRecyclerViewItem()

    data class ItemFilm(
        val id: Int,
        val title: String,
        val imageUrl: String
    ) : HomeRecyclerViewItem()

    data class ItemGenre(
        val title: String
    ) : HomeRecyclerViewItem()

}


fun Film.toHomeRecyclerViewItemFilm() = HomeRecyclerViewItem.ItemFilm(
    id = this.id,
    title = this.localized_name,
    imageUrl = this.image_url
)

fun String.toHomeRecyclerViewItemTitle() = HomeRecyclerViewItem.ItemTitle(
    text = this
)

fun String.toHomeRecyclerViewItemGenre() = HomeRecyclerViewItem.ItemGenre(
    title = this
)