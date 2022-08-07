package com.sangyan.easyfood.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.sangyan.easyfood.adapter.CategoryMealsAdapter
import com.sangyan.easyfood.adapter.MealsByCategoryName
import com.sangyan.easyfood.databinding.ActivityCategoryClassBinding
import com.sangyan.easyfood.ui.fragment.HomeFragment
import com.sangyan.easyfood.viewmodel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCategoryClassBinding
    private lateinit var viewModel: CategoryMealsViewModel
    private lateinit var categoryMealsAdapter: CategoryMealsAdapter

    companion object {
        const val MEAL_ID = "com.sangyan.easyfood.idMeal"
        const val MEAL_Name = "com.sangyan.easyfood.nameMeal"
        const val MEAL_THUMB = "com.sangyan.easyfood.thumbMeal"
        const val CATEGORY_NAME = "com.sangyan.easyfood.category"


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryClassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareRecyclerView()
        viewModel = ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]
        viewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        viewModel.observeMealsLiveData().observe(this, Observer { mealsList ->
            binding.tvCategoryCount.text = mealsList.size.toString()
            categoryMealsAdapter.setData(mealsList)
        })
  categoryMealsAdapter.setOnMealClickListener(object  : CategoryMealsAdapter.SetOnMealClickListener{
      override fun setOnClickListener(mealsByCategoryName: MealsByCategoryName) {
          val intent = Intent(applicationContext, MealActivity::class.java)
          intent.putExtra(MEAL_ID, mealsByCategoryName.idMeal)
          intent.putExtra(MEAL_Name, mealsByCategoryName.strMeal)
          intent.putExtra(MEAL_THUMB, mealsByCategoryName.strMealThumb)
          startActivity(intent)
      }

  })



    }
    private fun prepareRecyclerView() {
       categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter
        }
    }
}