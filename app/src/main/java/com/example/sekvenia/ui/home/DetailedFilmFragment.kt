package com.example.sekvenia.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import coil.load
import com.example.sekvenia.R
import com.example.sekvenia.databinding.FragmentDetailedFilmBinding
import com.example.sekvenia.domain.entity.Film
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class DetailedFilmFragment : Fragment(R.layout.fragment_detailed_film) {

    private var binding: FragmentDetailedFilmBinding? = null
    private val presenter: HomePresenter by inject { parametersOf(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filmIndex = arguments?.getInt("position") ?: 0
        binding = FragmentDetailedFilmBinding.bind(view)
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            presenter.filmStateFlow.collect {
                it.getOrNull(filmIndex)?.let { film ->
                    bindUi(film)
                }
            }
        }
    }

    private fun bindUi(film: Film) {
        binding?.apply {
            with(film) {
                textViewDetailedFilmTitle.text = name
                textViewDetailedFilmDescription.text = description
                textViewDetailedFilmRating.text = getString(R.string.rating, rating.toString())
                textViewDetailedFilmYear.text = getString(R.string.year, year.toString())
                imageViewDetailedFilmPoster.load(image_url)
                (requireActivity() as AppCompatActivity).supportActionBar?.title = localized_name
            }
        }

    }
}