package com.sangyan.easyfood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sangyan.easyfood.adapter.MealsByCategoryName
import com.sangyan.easyfood.db.MealDatabase
import com.sangyan.easyfood.api.RetrofitInstance
import com.sangyan.easyfood.model.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel  (
    private val mealDatabase: MealDatabase
        ): ViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<MealsByCategoryName>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var favoriteMealsLiveData = mealDatabase.mealDao().getAllMeals()
    fun getRandomMeals() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body()!=null){
                    val randomMeal : Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal

//                    Log.d("TEST","meal id ${randomMeal.idMeal} name ${randomMeal.strMeal}")
                }
                else{
                    return
                }

            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Home Fragment",t.message.toString())
            }

        })
    }
    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Vegetarian").enqueue(object : Callback<MealsByCategoryList>{
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                    if (response.body()!=null){
                        popularItemsLiveData.value = response.body()!!.meals
                    }
                   else{
                      return
                    }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                 Log.d("Home Fragment",t.message.toString())
            }

        })
    }
    fun getCategories() {
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
               response.body().let { categoryList ->  
                   categoriesLiveData.postValue(categoryList!!.categories)
               }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
               Log.d("Home View Model" , t.message.toString())
            }

        })
    }
    fun insertMeal(meal : Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }
    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }
    fun observePopularItemLiveData() : LiveData<List<MealsByCategoryName>>{
            return popularItemsLiveData
    }
    fun observeRandomMealLiveData() : LiveData<Meal>{
        return randomMealLiveData
    }
      fun observeCategoryLiveData() : LiveData<List<Category>> {
          return  categoriesLiveData
      }
    fun observeFavoriteMealsLiveData() : LiveData<List<Meal>>{
        return favoriteMealsLiveData
    }
}