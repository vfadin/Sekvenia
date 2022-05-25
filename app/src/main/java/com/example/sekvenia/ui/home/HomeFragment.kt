package com.example.sekvenia.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sekvenia.R
import com.example.sekvenia.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding? = null
    private val recyclerViewAdapter = HomeRecyclerViewAdapter()

    private val presenter: HomePresenter by inject { parametersOf(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        bindUi()
        viewLifecycleOwner.lifecycleScope.launch {
            recyclerViewAdapter.setUpdatedData(presenter.getFilms())
        }
    }

    private fun bindUi() {
        binding?.apply {
            with(recyclerViewFilms){
                adapter = recyclerViewAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }
}