package com.example.yummy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yummy.databinding.CategoryItemBinding
import com.example.yummy.ui.data.pojo.CategoriesList
import com.example.yummy.ui.data.pojo.Category

class CategoriesAdapter: RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>(){

    private var categoryList = ArrayList<Category>()
    lateinit var onItemClick: ((Category)->Unit)

    //A function to update category list
    fun setCategoryList(categoryList: List<Category>){
        this.categoryList = categoryList as ArrayList<Category>
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(val binding: CategoryItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {


       return CategoryViewHolder(
           CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
       )
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoryList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text = categoryList[position].strCategory

        holder.itemView.setOnClickListener{
            onItemClick(categoryList[position])
        }
    }
}