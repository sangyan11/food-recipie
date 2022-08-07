package com.sangyan.easyfood.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.sangyan.easyfood.ui.activity.MealActivity
import com.sangyan.easyfood.adapter.CountryAdapter
import com.sangyan.easyfood.viewmodel.CountryMealsViewModel
import com.sangyan.easyfood.databinding.FragmentCategoryBinding


class IndianFoodFragment : Fragment() {
 private lateinit var binding : FragmentCategoryBinding
 private lateinit var viewModel: CountryMealsViewModel
 private lateinit var countryAdapter : CountryAdapter
    companion object {
        const val MEAL_ID = "com.sangyan.easyfood.idMeal"
        const val MEAL_Name = "com.sangyan.easyfood.nameMeal"
        const val MEAL_THUMB = "com.sangyan.easyfood.thumbMeal"
        const val CATEGORY_NAME = "com.sangyan.easyfood.category"


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[CountryMealsViewModel::class.java]
        countryAdapter = CountryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentCategoryBinding.inflate(inflater,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareCountryItemsRecyclerView()
        viewModel.getIndianMeals()
        observeCountryItemsLiveData()
        onIndianMealsClick()


    }

    private fun onIndianMealsClick() {
        countryAdapter.onItemClick = { mealName->
             val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID,mealName.idMeal)
            intent.putExtra(MEAL_Name,mealName.strMeal)
            intent.putExtra(MEAL_THUMB,mealName.strMealThumb)
            startActivity(intent)
        }
    }

    private fun prepareCountryItemsRecyclerView() {
        binding.rvCountry.apply {
       layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter  = countryAdapter
        }
    }

    private fun observeCountryItemsLiveData() {
        viewModel.countryItemLiveData().observe(viewLifecycleOwner , Observer {  mealList->
        countryAdapter.setData(mealList)
        })
    }

}