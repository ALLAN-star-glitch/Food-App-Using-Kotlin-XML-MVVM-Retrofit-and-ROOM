package com.example.yummy.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.yummy.adapters.CategoryMealsAdapter
import com.example.yummy.databinding.ActivityCategoryMealsBinding
import com.example.yummy.mvvm.viewmodels.CategoryMealsViewModel
import com.example.yummy.ui.fragments.HomeFragment

class CategoryMealsActivity : AppCompatActivity() {

    // Declaring the binding instance
    private lateinit var binding: ActivityCategoryMealsBinding

    // Declaring the categoryMealsMVVM
    private lateinit var categoryMealsMVVM: CategoryMealsViewModel

    // Declaring the categoryMealsAdapter
    private lateinit var categoryMealsAdapter: CategoryMealsAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initializing the binding instance
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initializing the categoryMealsAdapter
        categoryMealsAdapter = CategoryMealsAdapter()

        // Initializing the categoryMealsMVVM
        categoryMealsMVVM = ViewModelProvider(this)[CategoryMealsViewModel::class.java]

        //Getting the meal name of the clicked category meal
        intent.getStringExtra(HomeFragment.CATEGORY_NAME)
            ?.let {mealName ->
                categoryMealsMVVM.getMealsByCategory(mealName) }

        // Preparing the RecyclerView
        prepareRecyclerView()

        // Observing the live data and updating the UI
        categoryMealsMVVM.filteredByCategoryMealsListLiveData.observe(this) { mealsList ->
            binding.tvCategoryCount.text = "Total Meals:" + mealsList.size.toString()
            categoryMealsAdapter.setFilteredMealsByCategory(mealsList)
        }
    }

    private fun prepareRecyclerView() {
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }
}
