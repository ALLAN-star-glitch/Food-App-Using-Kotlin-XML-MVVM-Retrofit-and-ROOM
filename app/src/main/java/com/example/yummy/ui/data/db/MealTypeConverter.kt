package com.example.yummy.ui.data.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters


@TypeConverters
class MealTypeConverter {


    //Room database will use this when it wants to insert data inside the table
    @TypeConverter
    fun fromAnyToString(attribute: Any?): String{

        if (attribute == null)
            return ""

        return attribute as String

    }


    //Room database will use this when it wants to retrieve data outside the table
    @TypeConverter
    fun fromStringToAny(attribute: String): Any{

        return attribute

    }
}