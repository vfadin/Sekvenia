package com.example.sekvenia.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
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

    private var dataList = mutableListOf<HomeRecyclerViewItem>()
    var selectedGenrePosition = -1
    private lateinit var listener: OnItemClickListener
    private lateinit var diffUtils: HomeDiffUtils
    private var genresCount = 0

    interface OnItemClickListener {
        fun onItemClick(position: Int, viewType: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    private fun redrawPrevSelectedGenre(position: Int) {
        selectedGenrePosition = if (selectedGenrePosition == -1) {
            position
        } else {
            notifyItemChanged(selectedGenrePosition)
            position
        }
    }

    fun setGenreFilter(position: Int, genreSet: Set<String>, filteredFilmList: List<Film>) {
        redrawPrevSelectedGenre(position)
        formDataList(filteredFilmList, genreSet)
    }

    private fun formDataList(dataList: List<Film>, genreSet: Set<String>) {
        val newDataList = mutableListOf<HomeRecyclerViewItem>()
        with(newDataList) {
            add("Жанры".toHomeRecyclerViewItemTitle())
            addAll(genreSet.map {
                it.toHomeRecyclerViewItemGenre()
            })
            add("Фильмы".toHomeRecyclerViewItemTitle())
            addAll(dataList.map {
                it.toHomeRecyclerViewItemFilm()
            })
        }
        diffUtils = HomeDiffUtils(this.dataList, newDataList)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffUtils)
        this.dataList = newDataList
        diffResult.dispatchUpdatesTo(this)
    }

    fun setUpdatedData(dataList: List<Film>, genreSet: Set<String>) {
        formDataList(dataList, genreSet)
        genresCount = genreSet.size
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
            is HomeRecyclerViewHolder.GenreViewHolder -> {
                holder.bind(
                    dataList[position] as HomeRecyclerViewItem.ItemGenre,
                    listener
                )
                if (position == selectedGenrePosition) {
                    holder.setSelectedBackground()
                }
            }
            is HomeRecyclerViewHolder.FilmViewHolder ->
                holder.bind(dataList[position] as HomeRecyclerViewItem.ItemFilm, listener)
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
                if (position < genresCount + 2) {
                    return 2
                }
                return 1
            }
        }
    }
}