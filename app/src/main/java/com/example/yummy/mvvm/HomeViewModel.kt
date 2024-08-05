package com.example.yummy.mvvm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yummy.ui.data.pojo.Meal
import com.example.yummy.ui.data.pojo.MealList
import com.example.yummy.ui.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(): ViewModel() {

    private var randromMealLiveData = MutableLiveData<Meal>()
    fun getRandomMeal(){
        RetrofitInstance.retrofit.getRandomMeal().enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
               if(response.body()!=null){
                   val randomMeal: Meal = response.body()!!.meals[0]
                   randromMealLiveData.value = randomMeal
               }else {
                   return
               }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment",t.message.toString())
            }
        })
    }

}