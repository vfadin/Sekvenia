package com.example.sekvenia.ui.home.detailedfilm

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import coil.load
import com.example.sekvenia.R
import com.example.sekvenia.databinding.FragmentDetailedFilmBinding
import com.example.sekvenia.domain.entity.Film
import com.example.sekvenia.ui.home.HomePresenter
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class DetailedFilmFragment : Fragment(R.layout.fragment_detailed_film) {

    private var binding: FragmentDetailedFilmBinding? = null
    private val presenter: HomePresenter by inject { parametersOf(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filmName = arguments?.getInt("localized_name") ?: 0
        binding = FragmentDetailedFilmBinding.bind(view)
        presenter.filteredFilmList.getOrNull(filmName)?.let {
            bindUi(it)
        }
    }

    private fun bindUi(film: Film) {
        binding?.apply {
            with(film) {
                textViewDetailedFilmTitle.text = name
                textViewDetailedFilmDescription.text = description
                textViewDetailedFilmRating.text = getString(R.string.rating, rating.toString())
                textViewDetailedFilmYear.text = getString(R.string.year, year.toString())
                imageViewDetailedFilmPoster.load(image_url) {
                    error(R.drawable.ph)
                }
                (requireActivity() as AppCompatActivity).supportActionBar?.title = localized_name
            }
        }

    }
}