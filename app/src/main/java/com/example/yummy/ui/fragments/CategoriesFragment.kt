package com.example.yummy.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.yummy.R
import com.example.yummy.adapters.CategoriesAdapter
import com.example.yummy.databinding.FragmentCategoriesBinding
import com.example.yummy.mvvm.viewmodels.GeneralViewModel
import com.example.yummy.ui.activities.MainActivity


class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var viewModel: GeneralViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).generalViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initialize the categoriesAdapter
        categoriesAdapter = CategoriesAdapter()

        prepareRecyclerView()
        observeCategories()
    }

    private fun observeCategories() {
        viewModel.allCatagoriesListLiveData.observe(viewLifecycleOwner){categories->

            categoriesAdapter.setCategoryList(categories)

        }
    }

    private fun prepareRecyclerView() {

        binding.rvCategoriesa.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }

    }

}