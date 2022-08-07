package com.sangyan.easyfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sangyan.easyfood.model.Category
import com.sangyan.easyfood.databinding.CategoryLayoutBinding

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
    private  var categoryList = ArrayList<Category>()
    var onItemClick : ((Category) -> Unit) ? =null
    fun setCategoryList(categoryList : List<Category>) {
        this.categoryList = categoryList as ArrayList<Category> /* = java.util.ArrayList<com.sangyan.easyfood.model.Category> */
        notifyDataSetChanged()
    }
    class CategoryViewHolder(val binding : CategoryLayoutBinding)  : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
       return  CategoryViewHolder(
           CategoryLayoutBinding.inflate(
               LayoutInflater.from(
                   parent.context
               )

           )
       )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
          Glide.with(holder.itemView)
              .load(categoryList[position].strCategoryThumb)
              .into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text = categoryList[position].strCategory
        holder.itemView.setOnClickListener{
            onItemClick!!.invoke(categoryList[position])
        }
    }

    override fun getItemCount(): Int {
       return categoryList.size
    }
}