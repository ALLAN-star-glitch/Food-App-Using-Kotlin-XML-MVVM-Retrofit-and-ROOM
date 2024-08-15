package com.example.yummy.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yummy.ui.data.pojo.FilteredByCategoryMeal
import com.example.yummy.ui.data.pojo.FilteredByCategoryMealsList
import com.example.yummy.ui.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel : ViewModel() {

    private val _filteredByCategoryMealsListLiveData = MutableLiveData<List<FilteredByCategoryMeal>>()
    val filteredByCategoryMealsListLiveData: LiveData<List<FilteredByCategoryMeal>>
        get() = _filteredByCategoryMealsListLiveData


    //function logic to get meals by category
    fun getMealsByCategory(categoryName: String){

        RetrofitInstance.retrofit.getMealByCategory(categoryName).enqueue(object : Callback<FilteredByCategoryMealsList>{
            override fun onResponse(
                call: Call<FilteredByCategoryMealsList>,
                response: Response<FilteredByCategoryMealsList>
            ) {
                response.body()?.let { mealsList ->
                    _filteredByCategoryMealsListLiveData.postValue(mealsList.meals)

                }
            }

            override fun onFailure(call: Call<FilteredByCategoryMealsList>, t: Throwable) {
                Log.d("encountered error", t.message.toString() )
            }
        })
    }



}