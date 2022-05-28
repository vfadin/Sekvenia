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
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HomeFragment : Fragment(R.layout.fragment_home) {

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

    private fun bindUi() {
        binding?.apply {
            with(recyclerViewHomeFilms) {
                val layoutManager = GridLayoutManager(requireContext(), 2)
                recyclerViewAdapter = HomeRecyclerViewAdapter(layoutManager)
                adapter = recyclerViewAdapter
                this.layoutManager = layoutManager
                recyclerViewAdapter.setOnItemClickListener(object :
                    HomeRecyclerViewAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int, viewType: Int) {
                        if (viewType == R.layout.item_home_film) {
                            val bundle = Bundle()
                            bundle.putInt("position", position)
                            findNavController().navigate(
                                R.id.action_homeFragment_to_detailedFilmFragment,
                                bundle
                            )
                        }
                    }
                })
            }
        }
    }
}