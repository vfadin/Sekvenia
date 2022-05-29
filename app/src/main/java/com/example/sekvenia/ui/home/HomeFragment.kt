package com.example.sekvenia.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sekvenia.R
import com.example.sekvenia.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import moxy.MvpAppCompatFragment
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HomeFragment : MvpAppCompatFragment(R.layout.fragment_home), IHomeView {

    private val TITLES_BEFORE_FILMS = 2
    private var binding: FragmentHomeBinding? = null
    private lateinit var recyclerViewAdapter: HomeRecyclerViewAdapter

    private val presenter: HomePresenter by inject { parametersOf(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        bindUi()
        viewLifecycleOwner.lifecycleScope.launch {
            presenter.filmStateFlow.collect {
                recyclerViewAdapter.setUpdatedData(it)
            }
        }
    }

    override fun bindUi() {
        binding?.apply {
            with(recyclerViewHomeFilms) {
                val layoutManager = GridLayoutManager(requireContext(), 2)
                recyclerViewAdapter = HomeRecyclerViewAdapter(layoutManager)
                adapter = recyclerViewAdapter
                this.layoutManager = layoutManager
                recyclerViewAdapter.setOnItemClickListener(object :
                    HomeRecyclerViewAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int, viewType: Int) {
                        when (viewType) {
                            R.layout.item_home_film -> {
                                val bundle = Bundle()
                                bundle.putString(
                                    "localized_name",
                                    recyclerViewAdapter.getFilmName(
                                        position -
                                                TITLES_BEFORE_FILMS -
                                                recyclerViewAdapter.getGenresCount()
                                    )?.localized_name
                                )
                                findNavController().navigate(
                                    R.id.action_homeFragment_to_detailedFilmFragment,
                                    bundle
                                )
                            }
                            R.layout.item_home_genre -> {
                                recyclerViewAdapter.setGenreFilter(position)
                            }
                        }
                    }
                })
            }
        }
    }
}