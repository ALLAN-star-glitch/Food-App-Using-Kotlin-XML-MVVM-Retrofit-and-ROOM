package com.example.yummy.mvvm.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yummy.ui.data.db.MealDatabase
import com.example.yummy.ui.data.pojo.Meal
import com.example.yummy.ui.data.pojo.MealList
import com.example.yummy.ui.data.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealAndDetailsViewModel(val mealdb: MealDatabase) : ViewModel() {

    private var _mealAndDetailsLiveData = MutableLiveData<Meal>() //live data
    val mealAndDetailsLiveData: LiveData<Meal>//mutable live data
        get() = _mealAndDetailsLiveData

    //Function to get meal and it's details using retrofit
    fun getMealAndDetails(id: String){
        RetrofitInstance.retrofit.getMealDetails(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!=null){
                    _mealAndDetailsLiveData.value = response.body()!!.meals[0]

                }else
                    return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MealActivity", t.message.toString())
            }
        })
    }



    //function to update and insert into the database
    fun upsertToDb(meal: Meal){
        viewModelScope.launch {
            mealdb.mealDao().upsert(meal)
        }
    }

    //function to delete a meal
    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealdb.mealDao().delete(meal)
        }
    }


}