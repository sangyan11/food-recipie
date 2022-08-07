package com.sangyan.easyfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sangyan.easyfood.databinding.MealsItemBinding

class CountryAdapter : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    class CountryViewHolder(val binding: MealsItemBinding)  : RecyclerView.ViewHolder(binding.root) {}
       private var mealList = ArrayList<MealsByCountryName>()
    var onItemClick : ((MealsByCountryName) -> Unit) ? =null
      fun setData(mealList : List<MealsByCountryName>) {
          this.mealList = mealList as ArrayList<MealsByCountryName>
          notifyDataSetChanged()
      }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(
            MealsItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text= mealList[position].strMeal
        holder.itemView.setOnClickListener{
            onItemClick!!.invoke(mealList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }
}