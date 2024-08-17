package com.example.yummy.ui.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yummy.ui.data.pojo.Meal


@Dao
interface MealDao {

    //Note that by adding the suspend keyword, we want the function to be called in a coroutines and we do not want to block the main thread since the function call may take some time
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)


    //this function does not need to be a suspend function because it returns a live data and a live data can be observed
    @Query("SELECT * FROM mealInformation")
    fun getAllMeal(): LiveData<List<Meal>>






}