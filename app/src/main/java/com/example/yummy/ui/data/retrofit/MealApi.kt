package com.example.yummy.ui.data.retrofit

import com.example.yummy.ui.data.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {

    @GET
    fun getRandomMeal(): Call<MealList>
}