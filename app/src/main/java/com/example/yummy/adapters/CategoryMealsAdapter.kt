package com.example.yummy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yummy.databinding.MealItemBinding
import com.example.yummy.ui.data.pojo.FilteredByCategoryMeal

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsAdapterViewHolder>() {

    // Initialize the list with an empty ArrayList
    private var filteredMealsByCategory: ArrayList<FilteredByCategoryMeal> = ArrayList()

    // A function to set the filteredMealsByCategory
    fun setFilteredMealsByCategory(filteredMealsByCategory: List<FilteredByCategoryMeal>) {
        this.filteredMealsByCategory = ArrayList(filteredMealsByCategory)
        notifyDataSetChanged()
    }

    class CategoryMealsAdapterViewHolder(val binding: MealItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsAdapterViewHolder {
        return CategoryMealsAdapterViewHolder(
            MealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return filteredMealsByCategory.size
    }

    override fun onBindViewHolder(holder: CategoryMealsAdapterViewHolder, position: Int) {
        val meal = filteredMealsByCategory[position]
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = meal.strMeal
    }
}
