package com.example.yummy.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.yummy.R
import com.example.yummy.databinding.ActivityMealBinding
import com.example.yummy.mvvm.MealViewModel
import com.example.yummy.ui.fragments.HomeFragment

class MealActivity : AppCompatActivity() {
    private lateinit var mealId: String
    private lateinit var mealThumb: String
    private lateinit var mealName: String
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealMVVM: MealViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Creating an instance of the meal view model
        mealMVVM = ViewModelProvider(this)[MealViewModel::class.java]

        //Function call to get the random meal information from the intent
        getMealInformationFromIntent()

        //Function call to set the information for the random meal ... image and the title
        setInformationInViews()

        //calling the getMealDetail function using the meal view model instance that we just created
        mealMVVM.getMealDetail(mealId)

        //A function call to observe meal detail live data
        observeMealDetailLiveData()


    }

    @SuppressLint("SetTextI18n")
    private fun observeMealDetailLiveData() {
        mealMVVM.observeMealDetailsLiveData().observe(this){mealDetails ->
            mealDetails?.apply {
                binding.tvCategoryInfo.text = "Category: ${mealDetails.strCategory}"
                binding.tvAreaInfo.text = "Location: ${mealDetails.strArea}"
                binding.tvContent.text = mealDetails.strInstructions

            }

        }
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME).toString()
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID).toString()
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB).toString()

    }
}