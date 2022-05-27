package com.example.sekvenia.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.example.sekvenia.R
import com.example.sekvenia.databinding.ItemHomeFilmBinding
import com.example.sekvenia.databinding.ItemHomeGenreBinding
import com.example.sekvenia.databinding.ItemHomeTitleBinding
import com.example.sekvenia.domain.entity.HomeRecyclerViewItem

sealed class HomeRecyclerViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class TitleViewHolder(private val binding: ItemHomeTitleBinding) : HomeRecyclerViewHolder(binding){
        fun bind(title: HomeRecyclerViewItem.Title) {
            binding.textViewTitle.text = title.text
        }
    }

    class FilmViewHolder(private val binding: ItemHomeFilmBinding) : HomeRecyclerViewHolder(binding){
        fun bind(film: HomeRecyclerViewItem.Film){
            binding.textViewLocalizedName.text = film.title
            binding.imageViewPoster.load(film.imageUrl) {
                placeholder(R.drawable.ic_launcher_foreground)
                scale(Scale.FILL)
            }
            itemView.setOnClickListener {
                println(film.title)
            }
        }
    }

    class GenreViewHolder(private val binding: ItemHomeGenreBinding) : HomeRecyclerViewHolder(binding){
        fun bind(genre: HomeRecyclerViewItem.Genre) {
            with(binding.buttonGenre) {
                text = genre.title
                setOnClickListener {
                    println(genre.title)
                    if(genre.isPressed) {
                        setBackgroundColor(resources.getColor(R.color.purple_200))
                        genre.isPressed = !genre.isPressed
                    }
                    else {
                        genre.isPressed = !genre.isPressed
                        setBackgroundColor(resources.getColor(R.color.black))
                    }
                }
            }
        }
    }
}
