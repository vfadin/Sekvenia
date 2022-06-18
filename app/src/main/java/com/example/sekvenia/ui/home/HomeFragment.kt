package com.example.sekvenia.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sekvenia.R
import com.example.sekvenia.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val TITLES_BEFORE_FILMS = 2
    private var binding: FragmentHomeBinding? = null
    private lateinit var recyclerViewAdapter: HomeRecyclerViewAdapter
    private val model: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        bindUi()
        setData()
    }

    private fun setData() {
        viewLifecycleOwner.lifecycleScope.launch {
            model.filmsStateFlow.collect {
                recyclerViewAdapter.setUpdatedData(it)
            }
        }
    }

    fun setGenreFilter(position: Int) {
        model.getFilteredFilmList(position)
    }

    private fun bindUi() {
        binding?.apply {
            with(recyclerViewHomeFilms) {
                val layoutManager = GridLayoutManager(requireContext(), 2)
                recyclerViewAdapter = HomeRecyclerViewAdapter(layoutManager)
                adapter = recyclerViewAdapter
//                recyclerViewAdapter.selectedGenrePosition = presenter.genreSelected
                this.layoutManager = layoutManager
                recyclerViewAdapter.setOnItemClickListener(object :
                    HomeRecyclerViewAdapter.OnItemClickListener {
                    override fun onItemClick(id: Int, viewType: Int) {
                        when (viewType) {
                            R.layout.item_home_film -> {
                                val bundle = Bundle()
                                bundle.putInt("id", id)
                                findNavController().navigate(
                                    R.id.action_homeFragment_to_detailedFilmFragment,
                                    bundle
                                )
                            }
                            R.layout.item_home_genre -> {
                                setGenreFilter(id)
                            }
                        }
                    }
                })
            }
            swipeRefreshLayoutHome.setOnRefreshListener {
                model.getFilms()
//                recyclerViewAdapter.redrawPrevSelectedGenre(presenter.genreSelected)
//                presenter.genreSelected = -1
                recyclerViewAdapter.selectedGenrePosition = -1
                swipeRefreshLayoutHome.isRefreshing = false
            }
        }
    }
    private fun setSpanCount(layoutManager: GridLayoutManager) {
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position < 13 + 2) {
                    return 2
                }
                return 1
            }
        }
    }
}