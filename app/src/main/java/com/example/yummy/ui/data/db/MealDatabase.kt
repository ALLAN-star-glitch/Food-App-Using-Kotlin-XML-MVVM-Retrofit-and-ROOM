package com.example.yummy.ui.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.yummy.ui.data.pojo.Meal


@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase : RoomDatabase(){
    abstract fun mealDao(): MealDao

    //a function in a companion object to return an instance from this database class

    companion object{

        @Volatile // this annotation means that any change of this interview from any thread will be visible to any other thread
        var INSTANCE: MealDatabase? = null


        @Synchronized // means that only one thread can have an instance from this database
        fun getInstance(context: Context): MealDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration() //note that fallbackToDestructiveMigration ensures the database is rebuild when one changes the version instead of deleting the data in it...
                    .build()
            }

            return INSTANCE as MealDatabase

        }
    }
}