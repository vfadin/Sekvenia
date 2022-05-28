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
        fun bind(
            genre: HomeRecyclerViewItem.ItemGenre,
            listener: HomeRecyclerViewAdapter.OnItemClickListener
        ) {
            with(binding) {
                textViewGenre.text = genre.title
                itemView.setOnClickListener {
                    with(cardView) {
                        if (isSelected) {
                            setCardBackgroundColor(context.resources.getColor(R.color.dark_gray))
                            textViewGenre.setBackgroundColor(
                                context.resources.getColor(R.color.dark_gray)
                            )
                            isSelected = false
                        } else {
                            listener.onItemClick(adapterPosition, R.layout.item_home_genre)
                            isSelected = true
                            setCardBackgroundColor(context.resources.getColor(R.color.purple_200))
                            textViewGenre.setBackgroundColor(
                                context.resources.getColor(R.color.purple_200)
                            )
                        }
                    }

                }
            }
        }
    }
}
