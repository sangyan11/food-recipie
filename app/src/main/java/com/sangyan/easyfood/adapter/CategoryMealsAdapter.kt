package com.sangyan.easyfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sangyan.easyfood.databinding.MealsItemBinding
import com.sangyan.easyfood.model.Meal

class CategoryMealsAdapter  : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {
    class CategoryMealsViewHolder (val binding : MealsItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    private var mealsList = ArrayList<MealsByCategoryName>()
    private lateinit var setOnMealClickListener : SetOnMealClickListener
    fun setOnMealClickListener(setOnMealClickListener: SetOnMealClickListener){
        this.setOnMealClickListener = setOnMealClickListener
    }



    fun setData(mealsList : List<MealsByCategoryName>){
        this.mealsList = mealsList as ArrayList<MealsByCategoryName> /* = java.util.ArrayList<com.sangyan.easyfood.adapter.MealsByCategoryName> */
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
         return  CategoryMealsViewHolder(
             MealsItemBinding.inflate(
                 LayoutInflater.from(parent.context)
             )
         )
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
       Glide.with(holder.itemView)
           .load(mealsList[position].strMealThumb)
           .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text= mealsList[position].strMeal
        holder.itemView.setOnClickListener {
            setOnMealClickListener.setOnClickListener(mealsList[position])
        }


    }

    override fun getItemCount(): Int {
         return mealsList.size
    }
    interface SetOnMealClickListener {
        fun setOnClickListener(mealsByCategoryName: MealsByCategoryName)
    }
}