package com.example.yummy.mvvm.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yummy.mvvm.viewmodels.MealAndDetailsViewModel
import com.example.yummy.ui.data.db.MealDatabase

class MealViewModelFactory(private val mealDatabase: MealDatabase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealAndDetailsViewModel(mealDatabase) as T
    }
}