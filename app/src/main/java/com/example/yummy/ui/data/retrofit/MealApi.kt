package com.example.yummy.ui.data.retrofit

import com.example.yummy.ui.data.pojo.CategoriesList
import com.example.yummy.ui.data.pojo.FilteredByCategoryMealsList
import com.example.yummy.ui.data.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id: String): Call<MealList>

    //function to get popular meals
    @GET("filter.php?")
    fun getPopularMeals(
        @Query("c")
        categoryName: String
    ): Call<FilteredByCategoryMealsList>

    //function to get all categories
    @GET("categories.php")
    fun getAllCategories(): Call<CategoriesList>

    //A function to filter meals by category
    @GET("filter.php")
    fun getMealByCategory(
        @Query("c")
        categoryName: String
    ): Call<FilteredByCategoryMealsList>
}