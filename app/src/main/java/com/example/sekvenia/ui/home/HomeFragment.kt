package com.example.sekvenia.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sekvenia.R
import com.example.sekvenia.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding? = null
    private val recyclerViewAdapter = HomeRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        bindUi()
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