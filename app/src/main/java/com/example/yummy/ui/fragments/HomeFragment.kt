package com.example.yummy.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.yummy.adapters.CategoriesAdapter
import com.example.yummy.adapters.PopularMealsAdapter
import com.example.yummy.databinding.FragmentHomeBinding
import com.example.yummy.mvvm.HomeViewModel
import com.example.yummy.ui.activities.CategoryMealsActivity
import com.example.yummy.ui.activities.MealActivity
import com.example.yummy.ui.data.pojo.Category
import com.example.yummy.ui.data.pojo.FilteredMealBySpecificCategory
import com.example.yummy.ui.data.pojo.Meal

class HomeFragment : Fragment() {

    private lateinit var randomMeal: Meal
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var popularItemsAdapter: PopularMealsAdapter
    private lateinit var categoryAdapter: CategoriesAdapter

    companion object{
        const val MEAL_ID = "Meal ID"
        const val MEAL_NAME = "Meal Name"
        const val MEAL_THUMB = "Meal Thumb"
        const val CATEGORY_NAME = "Category Name"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java] //initializing homeMvvm
        popularItemsAdapter = PopularMealsAdapter() //initializing popularItemsAdapter
        categoryAdapter = CategoriesAdapter() //initializing the categories adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()
        homeMvvm.getRandomMeal()
        observerRandomMeal()

        //function call for clicking the random meal image
        onRandomMealClick()


        //function call to the getPopularItems in the home viewmodel
        homeMvvm.getPopularItems()

        //function call to observe the popular meals live data in the home view model
        observePopularItemsLiveData()

        //a function call to invoke an event when a a popular food image is clicked
        onPopularFoodClick()

        //A function call to prepare our recyclerview for the categories
        prepareCategoriesRecyclerView()

        //A function call to get the all meals categories
        homeMvvm.getAllMealsCategories()

        //A function call to observe the allCategoriesLiveData that is in the home view model
        observeAllCategoriesLiveData()

        //A function call to handle the meal item in a category on the category section of the app home fragment
        onCategoryClick()
    }

    private fun onCategoryClick() {
        categoryAdapter.onItemClick = { category ->
            val intent = Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)

        }
    }

    private fun prepareCategoriesRecyclerView() {
        binding.recCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }

    private fun observeAllCategoriesLiveData() {
        homeMvvm.observeAllCategoriesLiveData().observe(viewLifecycleOwner){mealCategories ->
            categoryAdapter.setCategoryList(mealCategories as ArrayList<Category>)

        }
    }

    private fun onPopularFoodClick() {
        popularItemsAdapter.onItemClick = { mealInfo ->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,mealInfo.idMeal)
            intent.putExtra(MEAL_NAME,mealInfo.strMeal)
            intent.putExtra(MEAL_THUMB,mealInfo.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewPopular.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        homeMvvm.observePopularMealsLiveData().observe(viewLifecycleOwner) { popularMeals ->
            popularItemsAdapter.setMeals(mealList = popularMeals as ArrayList<FilteredMealBySpecificCategory>)

        }

    }

    private fun onRandomMealClick() {
        binding.randomCardImage.setOnClickListener{

            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)

            startActivity(intent)

        }
    }

    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner) { meal ->
            meal?.let {
                Glide.with(this@HomeFragment)
                    .load(it.strMealThumb)
                    .into(binding.imgRandom)
                
                this.randomMeal = meal
            }
        }
    }
}
