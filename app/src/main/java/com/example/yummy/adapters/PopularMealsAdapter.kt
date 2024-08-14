package com.example.yummy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yummy.databinding.PopularMealsBinding
import com.example.yummy.ui.data.pojo.FilteredMealBySpecificCategory


class PopularMealsAdapter(): RecyclerView.Adapter<PopularMealsAdapter.PopularMealViewHolder>() {
    lateinit var onItemClick: ((FilteredMealBySpecificCategory)->Unit)
    private var mealsList = ArrayList<FilteredMealBySpecificCategory>()

    //a function to set the meals list in a category
    fun setMeals(mealList: ArrayList<FilteredMealBySpecificCategory>){
        this.mealsList = mealList
        notifyDataSetChanged() //to reset the adapter to update the view on every change
    }

    class PopularMealViewHolder(val binding:PopularMealsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularMealsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener{
            onItemClick(mealsList[position])
        }
    }
}