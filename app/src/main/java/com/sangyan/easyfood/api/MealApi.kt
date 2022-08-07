package com.sangyan.easyfood.api

import com.sangyan.easyfood.model.CategoryList
import com.sangyan.easyfood.model.MealList
import com.sangyan.easyfood.model.MealsByCategoryList
import com.sangyan.easyfood.model.MealsByCountryList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    fun getRandomMeal() : Call<MealList>
    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id : String) : Call<MealList>
    @GET("filter.php?")
    fun getPopularItems(@Query("c") categoryName : String) : Call<MealsByCategoryList>
     @GET("categories.php")
    fun  getCategories() : Call<CategoryList>
    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName : String) : Call<MealsByCategoryList>
    @GET("filter.php?")
    fun getMealsByCountryName(@Query("a") countryName : String) : Call<MealsByCountryList>


}