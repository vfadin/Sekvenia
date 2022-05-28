package com.example.sekvenia.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sekvenia.R
import com.example.sekvenia.databinding.ItemHomeFilmBinding
import com.example.sekvenia.databinding.ItemHomeGenreBinding
import com.example.sekvenia.databinding.ItemHomeTitleBinding
import com.example.sekvenia.domain.entity.*

class HomeRecyclerViewAdapter(
    private val layoutManager: GridLayoutManager
) : RecyclerView.Adapter<HomeRecyclerViewHolder>() {

    private var dataList: MutableList<HomeRecyclerViewItem> = mutableListOf()
    private var genreSet = mutableSetOf<String>()
    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int, viewType: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun getGenresCount() = genreSet.size

    fun setUpdatedData(dataList: List<Film>) {
        this.dataList.clear()
        dataList.map {
            genreSet.addAll(it.genres)
        }
        with(this.dataList) {
            add("Жанры".toHomeRecyclerViewItemTitle())
            addAll(genreSet.map {
                it.toHomeRecyclerViewItemGenre()
            })
            add("Фильмы".toHomeRecyclerViewItemTitle())
            addAll(dataList.map {
                it.toHomeRecyclerViewItemFilm()
            })
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): HomeRecyclerViewHolder {
        return when (viewType) {
            R.layout.item_home_title -> HomeRecyclerViewHolder.TitleViewHolder(
                ItemHomeTitleBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
            )
            R.layout.item_home_film -> HomeRecyclerViewHolder.FilmViewHolder(
                ItemHomeFilmBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
            )
            R.layout.item_home_genre -> HomeRecyclerViewHolder.GenreViewHolder(
                ItemHomeGenreBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
            )
            else -> {
                throw IllegalArgumentException("Invalid ViewType Provided")
            }
        }
    }

    override fun onBindViewHolder(holder: HomeRecyclerViewHolder, position: Int) {
        setSpanCount()
        when (holder) {
            is HomeRecyclerViewHolder.GenreViewHolder ->
                holder.bind(dataList[position] as HomeRecyclerViewItem.ItemGenre, listener)
            is HomeRecyclerViewHolder.FilmViewHolder -> {
                holder.bind(dataList[position] as HomeRecyclerViewItem.ItemFilm, listener)
            }
            is HomeRecyclerViewHolder.TitleViewHolder ->
                holder.bind(dataList[position] as HomeRecyclerViewItem.ItemTitle)
        }
    }

    override fun getItemCount() = dataList.size

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position]) {
            is HomeRecyclerViewItem.ItemFilm -> R.layout.item_home_film
            is HomeRecyclerViewItem.ItemGenre -> R.layout.item_home_genre
            is HomeRecyclerViewItem.ItemTitle -> R.layout.item_home_title
        }
    }

    private fun setSpanCount() {
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position < getGenresCount() + 2) {
                    return 2
                }
                return 1
            }
        }
    }
}