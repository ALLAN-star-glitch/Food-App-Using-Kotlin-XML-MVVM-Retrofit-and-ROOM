package com.example.yummy.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yummy.ui.data.pojo.CategoriesList
import com.example.yummy.ui.data.pojo.Category
import com.example.yummy.ui.data.pojo.FilteredMealBySpecificCategory
import com.example.yummy.ui.data.pojo.FilteredBySpecificCategoryMealsList
import com.example.yummy.ui.data.pojo.Meal
import com.example.yummy.ui.data.pojo.MealList
import com.example.yummy.ui.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val randomMealLiveData = MutableLiveData<Meal>()
    private val popularItemsLiveData = MutableLiveData<List<FilteredMealBySpecificCategory>>()
    private val allCategoriesListLiveData = MutableLiveData<List<Category>>()

    fun getRandomMeal() {
        RetrofitInstance.retrofit.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                response.body()?.let { mealList ->
                    if (mealList.meals.isNotEmpty()) {
                        val randomMeal: Meal = mealList.meals[0]
                        randomMealLiveData.value = randomMeal
                    } else {
                        Log.d("HomeViewModel", "Meal list is empty")
                    }
                } ?: Log.d("HomeViewModel", "Response body is null")
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeViewModel", t.message.toString())
            }
        })
    }

    fun getPopularItems(){
        RetrofitInstance.retrofit.getPopularMeals("Seafood").enqueue(object : Callback<FilteredBySpecificCategoryMealsList>{
            override fun onResponse(call: Call<FilteredBySpecificCategoryMealsList>, response: Response<FilteredBySpecificCategoryMealsList>) {
                response.body()?.let {popularMeals ->
                    if (response.body()!=null){
                        popularItemsLiveData.value = popularMeals.meals
                    }

                }
            }

            override fun onFailure(call: Call<FilteredBySpecificCategoryMealsList>, t: Throwable) {
                Log.d("HomeFragment",t.message.toString())
            }
        })
    }

    //a function to return random meal live data for observation
    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

    //A function to return popular meals live data for observation
    fun observePopularMealsLiveData(): LiveData<List<FilteredMealBySpecificCategory>>{
        return popularItemsLiveData
    }

    //A function to get all categories
    fun getAllMealsCategories(){
        RetrofitInstance.retrofit.getAllCategories().enqueue(object : Callback<CategoriesList>{
            override fun onResponse(
                call: Call<CategoriesList>,
                response: Response<CategoriesList>
            ) {
                response.body()?.let {mealsCategoryList ->
                    allCategoriesListLiveData.postValue(mealsCategoryList.categories)

                }
            }

            override fun onFailure(call: Call<CategoriesList>, t: Throwable) {
                Log.e("HomeFragment", t.message.toString())
            }
        })
    }

    //A function that will help us to observe the all categories live data ... it will return a live data.
    fun observeAllCategoriesLiveData(): LiveData<List<Category>>{
        return allCategoriesListLiveData
    }
}
