package com.sangyan.easyfood.ui.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager

import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.sangyan.easyfood.ui.activity.CategoryMealsActivity
import com.sangyan.easyfood.ui.activity.MainActivity
import com.sangyan.easyfood.ui.activity.MealActivity
import com.sangyan.easyfood.adapter.CategoriesAdapter
import com.sangyan.easyfood.adapter.MealsByCategoryName
import com.sangyan.easyfood.viewmodel.HomeViewModel
import com.sangyan.easyfood.adapter.MostPopularAdapter
import com.sangyan.easyfood.databinding.FragmentHomeBinding
import com.sangyan.easyfood.model.Meal

class HomeFragment : Fragment() {
private lateinit var binding : FragmentHomeBinding
private lateinit var homeMVVM : HomeViewModel
private lateinit var randomMeal : Meal
private lateinit var popularItemsAdapter : MostPopularAdapter
private lateinit var categoriesAdapter: CategoriesAdapter
companion object {
    const val MEAL_ID = "com.sangyan.easyfood.idMeal"
    const val MEAL_Name = "com.sangyan.easyfood.nameMeal"
    const val MEAL_THUMB = "com.sangyan.easyfood.thumbMeal"
    const val CATEGORY_NAME = "com.sangyan.easyfood.category"


}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       homeMVVM = (activity as MainActivity).viewModel
        popularItemsAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return   binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preparePopularItemsRecyclerView()
        homeMVVM.getRandomMeals()
        observeRandomMeal()
        onRandomMealClick()
        homeMVVM.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemClick()
        prepareCategoriesRecyclerView()
        homeMVVM.getCategories()
        observeCategoriseLiveData()
        onCategoryClick()

    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->  
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recViewCategory.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriseLiveData() {
        homeMVVM.observeCategoryLiveData().observe(viewLifecycleOwner, Observer { categories->
          categoriesAdapter.setCategoryList(categories)
        })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_Name,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)

        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter =  popularItemsAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        homeMVVM.observePopularItemLiveData().observe(viewLifecycleOwner
        ) {  mealList->
            popularItemsAdapter.setMeals( mealList= mealList as ArrayList<MealsByCategoryName> )
        }
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener{
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_Name,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        homeMVVM.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)
            this.randomMeal = meal
        }
    }

}