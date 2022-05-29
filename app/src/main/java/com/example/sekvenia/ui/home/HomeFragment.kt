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
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HomeFragment : MvpAppCompatFragment(R.layout.fragment_home), IHomeView {

    private val TITLES_BEFORE_FILMS = 2
    private var binding: FragmentHomeBinding? = null
    private lateinit var recyclerViewAdapter: HomeRecyclerViewAdapter

    @InjectPresenter
    lateinit var presenter: HomePresenter

    @ProvidePresenter
    fun provide(): HomePresenter = get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
    }

    override fun setData() {
        viewLifecycleOwner.lifecycleScope.launch {
            presenter.filmStateFlow.collect {
                recyclerViewAdapter.setUpdatedData(
                    presenter.filteredFilmList,
                    presenter.genreSet
                )
            }
        }

    }

    override fun setGenreFilter(position: Int) {
        recyclerViewAdapter.setGenreFilter(
            position,
            presenter.genreSet,
            presenter.getFilteredFilmList(position)
        )
    }

    override fun bindUi() {
        binding?.apply {
            with(recyclerViewHomeFilms) {
                val layoutManager = GridLayoutManager(requireContext(), 2)
                recyclerViewAdapter = HomeRecyclerViewAdapter(layoutManager)
                adapter = recyclerViewAdapter
                recyclerViewAdapter.selectedGenrePosition = presenter.genreSelected
                this.layoutManager = layoutManager
                recyclerViewAdapter.setOnItemClickListener(object :
                    HomeRecyclerViewAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int, viewType: Int) {
                        when (viewType) {
                            R.layout.item_home_film -> {
                                val bundle = Bundle()
                                bundle.putInt(
                                    "localized_name",
                                    position - TITLES_BEFORE_FILMS -
                                            presenter.genreSet.size
                                )
                                findNavController().navigate(
                                    R.id.action_homeFragment_to_detailedFilmFragment,
                                    bundle
                                )
                            }
                            R.layout.item_home_genre -> {
                                setGenreFilter(position)
                            }
                        }
                    }
                })
            }
        }
        setData()
    }
}