package com.example.sekvenia.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import coil.load
import coil.size.Scale
import com.example.sekvenia.R
import com.example.sekvenia.databinding.ItemHomeFilmBinding
import com.example.sekvenia.databinding.ItemHomeGenreBinding
import com.example.sekvenia.databinding.ItemHomeTitleBinding
import com.example.sekvenia.domain.entity.HomeRecyclerViewItem

sealed class HomeRecyclerViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class TitleViewHolder(private val binding: ItemHomeTitleBinding) :
        HomeRecyclerViewHolder(binding) {
        fun bind(title: HomeRecyclerViewItem.ItemTitle) {
            binding.textViewTitle.text = title.text
        }
    }

    class FilmViewHolder(private val binding: ItemHomeFilmBinding) :
        HomeRecyclerViewHolder(binding) {
        fun bind(
            film: HomeRecyclerViewItem.ItemFilm,
            listener: HomeRecyclerViewAdapter.OnItemClickListener
        ) {
            binding.textViewLocalizedName.text = film.title
            binding.imageViewPoster.load(film.imageUrl) {
                placeholder(R.drawable.ic_launcher_foreground)
                scale(Scale.FILL)
            }
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition, R.layout.item_home_film)
            }
        }
    }

    class GenreViewHolder(private val binding: ItemHomeGenreBinding) :
        HomeRecyclerViewHolder(binding) {

        fun setSelectedBackground() {
            with(binding) {
                with(cardView) {
                    isSelected = true
                    setCardBackgroundColor(context.resources.getColor(R.color.purple_200))
                    textViewGenre.setBackgroundColor(
                        context.resources.getColor(R.color.purple_200)
                    )
                }
            }
        }

        fun setDefaultBackground(genre: HomeRecyclerViewItem.ItemGenre) {
            with(binding) {
                with(cardView) {
                    isSelected = false
                    setCardBackgroundColor(context.resources.getColor(R.color.dark_gray))
                    textViewGenre.setBackgroundColor(
                        context.resources.getColor(R.color.dark_gray)
                    )
                }
            }
        }

        fun bind(
            genre: HomeRecyclerViewItem.ItemGenre,
            listener: HomeRecyclerViewAdapter.OnItemClickListener
        ): Int? {
            with(binding) {
                textViewGenre.text = genre.title
                itemView.setOnClickListener {
                    if (!cardView.isSelected) {
                        setSelectedBackground()
                        listener.onItemClick(
                            adapterPosition,
                            R.layout.item_home_genre
                        )
                    } else setDefaultBackground(genre)
                }
            }
            println("null")
            return null
        }
    }
}
