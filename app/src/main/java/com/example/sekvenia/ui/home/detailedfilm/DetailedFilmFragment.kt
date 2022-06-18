package com.example.sekvenia.ui.home.detailedfilm

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.sekvenia.R
import com.example.sekvenia.databinding.FragmentDetailedFilmBinding
import com.example.sekvenia.domain.entity.Film
import com.example.sekvenia.ui.home.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailedFilmFragment : Fragment(R.layout.fragment_detailed_film) {

    private var binding: FragmentDetailedFilmBinding? = null
    private val model : HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id") ?: 0
        binding = FragmentDetailedFilmBinding.bind(view)
        viewLifecycleOwner.lifecycleScope.launch {
            model.detailedFilmStateFlow.collect { list ->
                list.map {
                    println(it.id)
                    if(it.id == id) {
                        bindUi(it)
                    }
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
                imageViewDetailedFilmPoster.load(image_url) {
                    error(R.drawable.ph)
                }
                (requireActivity() as AppCompatActivity).supportActionBar?.title = localized_name
            }
        }

    }
}