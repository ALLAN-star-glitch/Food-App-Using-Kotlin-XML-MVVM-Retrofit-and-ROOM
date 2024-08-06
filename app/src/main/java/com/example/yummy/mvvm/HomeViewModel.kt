package com.example.yummy.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yummy.ui.data.pojo.Meal
import com.example.yummy.ui.data.pojo.MealList
import com.example.yummy.ui.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val randomMealLiveData = MutableLiveData<Meal>()

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

    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }
}
