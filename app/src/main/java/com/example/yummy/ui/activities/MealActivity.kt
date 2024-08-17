package com.example.yummy.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.yummy.R
import com.example.yummy.databinding.ActivityMealBinding
import com.example.yummy.mvvm.viewmodels.MealAndDetailsViewModel
import com.example.yummy.mvvm.viewmodelfactories.MealViewModelFactory
import com.example.yummy.ui.data.db.MealDatabase
import com.example.yummy.ui.data.pojo.Meal
import com.example.yummy.ui.fragments.HomeFragment

class MealActivity : AppCompatActivity() {
    private lateinit var mealId: String
    private lateinit var mealThumb: String
    private lateinit var mealName: String
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealMVVM: MealAndDetailsViewModel
    private lateinit var youtubeLink: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //creating an instance of the meal database
        val mealdb = MealDatabase.getInstance(this)

        //Creating an instance of the meal view model

        //creating an instance of viewmodel provider factory that will enable us to create an instance of the meal view model
        val viewModelFactory = MealViewModelFactory(mealdb)

        //creating an instance of the meal view model
        mealMVVM = ViewModelProvider(this, viewModelFactory)[MealAndDetailsViewModel::class.java]

        //mealMVVM = ViewModelProvider(this)[MealViewModel::class.java]

        //Function call to get the random meal information from the intent
        getMealInformationFromIntent()


        //Function call to set the information for the random meal ... image and the title
        setInformationInViews()

        //Function call to invoke loading case ... the call is just before requesting for details from the api through the method that is in the viewmodel
        loadingCase()

        //calling the getMealDetail function using the meal view model instance that we just created
        mealMVVM.getMealAndDetails(mealId)

        //A function call to observe meal detail live data
        observeMealDetailLiveData()

        //function call for clicking the youtube image
        onYouTubeImageClick()

        //function call for clicking the favorite icon
        onFavoriteClick()



    }

    private fun onFavoriteClick() {
        binding.btnAddToFavorite.setOnClickListener{

            mealToSave?.let {favmeal->
                mealMVVM.upsertToDb(favmeal)
                Toast.makeText(this,"Meal saved to favorites",Toast.LENGTH_SHORT).show()

            }


        }
    }

    private fun onYouTubeImageClick() {
        binding.youtube.setOnClickListener(){
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }


    private var mealToSave: Meal? = null
    @SuppressLint("SetTextI18n")
    private fun observeMealDetailLiveData() {
        mealMVVM.mealAndDetailsLiveData.observe(this){mealDetails ->
            mealDetails?.apply{

                //function call to invoke response case ... we call this function here since on observing the live data, when the live data changes, we are getting a response
                onResponseCase()

                mealToSave = mealDetails
                binding.tvCategoryInfo.text = "Category: ${mealDetails.strCategory}"
                binding.tvAreaInfo.text = "Location: ${mealDetails.strArea}"
                binding.tvContent.text = mealDetails.strInstructions
                youtubeLink = mealDetails.strYoutube.toString()

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


    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.btnAddToFavorite.visibility = View.INVISIBLE
        binding.youtube.visibility = View.INVISIBLE
        binding.tvAreaInfo.visibility = View.INVISIBLE
        binding.tvCategoryInfo.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.btnAddToFavorite.visibility = View.VISIBLE
        binding.youtube.visibility = View.VISIBLE
        binding.tvAreaInfo.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
    }
}