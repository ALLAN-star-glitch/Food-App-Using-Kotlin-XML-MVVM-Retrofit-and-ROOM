/*This view model will handle general logic for the app. For instance, it will handle
* the logic for the favorite fragment, home fragment as well as the categories fragment.
* Therefore, this view model will be initialized in the main activity, and then used in the aforementioned
* fragments.
* Als, this view model will take in mealDatabase as it's dependency, hence a necessity to create the
* generalViewModelFactory*/



package com.example.yummy.mvvm.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yummy.ui.data.db.MealDatabase
import com.example.yummy.ui.data.pojo.CategoriesList
import com.example.yummy.ui.data.pojo.Category
import com.example.yummy.ui.data.pojo.FilteredByCategoryMeal
import com.example.yummy.ui.data.pojo.FilteredByCategoryMealsList
import com.example.yummy.ui.data.pojo.Meal
import com.example.yummy.ui.data.pojo.MealList
import com.example.yummy.ui.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GeneralViewModel(val mealDatabase: MealDatabase) : ViewModel() {


    private val _randomMealLiveData = MutableLiveData<Meal>()
    val randromMealLiveData: LiveData<Meal> //for observation of the random meal
        get() = _randomMealLiveData

    private val _popularMealsLiveData = MutableLiveData<List<FilteredByCategoryMeal>>()
    val popularMealsLiveData: LiveData<List<FilteredByCategoryMeal>>
        get() = _popularMealsLiveData

    private val _allCategoriesListLiveData = MutableLiveData<List<Category>>()
    val allCatagoriesListLiveData: LiveData<List<Category>>
        get() = _allCategoriesListLiveData


    //we define the favoriteMeals variable in the home view model
    private var favoriteMealsLiveData = mealDatabase.mealDao().getAllMeal()


    fun getRandomMeal() {
        RetrofitInstance.retrofit.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                response.body()?.let { mealList ->
                    if (mealList.meals.isNotEmpty()) {
                        val randomMeal: Meal = mealList.meals[0]
                        _randomMealLiveData.value = randomMeal
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
        RetrofitInstance.retrofit.getPopularMeals("Seafood").enqueue(object : Callback<FilteredByCategoryMealsList>{
            override fun onResponse(call: Call<FilteredByCategoryMealsList>, response: Response<FilteredByCategoryMealsList>) {
                response.body()?.let {popularMeals ->
                    if (response.body()!=null){
                        _popularMealsLiveData.value = popularMeals.meals
                    }

                }
            }

            override fun onFailure(call: Call<FilteredByCategoryMealsList>, t: Throwable) {
                Log.d("HomeFragment",t.message.toString())
            }
        })
    }


    //A function to get all categories
    fun getAllMealsCategories(){
        RetrofitInstance.retrofit.getAllCategories().enqueue(object : Callback<CategoriesList>{
            override fun onResponse(
                call: Call<CategoriesList>,
                response: Response<CategoriesList>
            ) {
                response.body()?.let {mealsCategoryList ->
                    _allCategoriesListLiveData.postValue(mealsCategoryList.categories)

                }
            }

            override fun onFailure(call: Call<CategoriesList>, t: Throwable) {
                Log.e("HomeFragment", t.message.toString())
            }
        })
    }


    //A function that will return favoriteMealsLiveData for observation purpose
    fun observeFavoriteMealLiveData(): LiveData<List<Meal>>{
        return favoriteMealsLiveData
    }
}
