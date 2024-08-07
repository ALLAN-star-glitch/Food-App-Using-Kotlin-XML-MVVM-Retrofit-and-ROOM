package com.example.yummy.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.yummy.databinding.FragmentHomeBinding
import com.example.yummy.mvvm.HomeViewModel
import com.example.yummy.ui.activities.MealActivity
import com.example.yummy.ui.data.pojo.Meal

class HomeFragment : Fragment() {

    private lateinit var randomMeal: Meal
    private lateinit var binding: FragmentHomeBinding
    private val homeMvvm: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    companion object{
        const val MEAL_ID = "Meal ID"
        const val MEAL_NAME = "Meal Name"
        const val MEAL_THUMB = "Meal Thumb"

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeMvvm.getRandomMeal()
        observerRandomMeal()

        //function call for clicking the random meal image
        onRandomMealClick()
    }

    private fun onRandomMealClick() {
        binding.randomCardImage.setOnClickListener{

            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)

            startActivity(intent)

        }
    }

    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner) { meal ->
            meal?.let {
                Glide.with(this@HomeFragment)
                    .load(it.strMealThumb)
                    .into(binding.imgRandom)
                
                this.randomMeal = meal
            }
        }
    }
}
