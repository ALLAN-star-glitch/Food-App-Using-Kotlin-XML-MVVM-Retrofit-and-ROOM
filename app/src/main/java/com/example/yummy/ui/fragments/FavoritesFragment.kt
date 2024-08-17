package com.example.yummy.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.yummy.R
import com.example.yummy.adapters.FavoriteMealAdapter
import com.example.yummy.databinding.FragmentFavoritesBinding
import com.example.yummy.mvvm.viewmodels.GeneralViewModel
import com.example.yummy.ui.activities.MainActivity


class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: GeneralViewModel
    private lateinit var favoriteAdapter: FavoriteMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).generalViewModel //initializing the view model


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFavoritesBinding.inflate(inflater)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeFavorites()
        prepareRecyclerView()
    }

    private fun prepareRecyclerView() {
       favoriteAdapter = FavoriteMealAdapter()
        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favoriteAdapter
        }
    }

    private fun observeFavorites() {
        viewModel.observeFavoriteMealLiveData().observe(viewLifecycleOwner){meals->
            favoriteAdapter.differ.submitList(meals)

        }
    }


}