package com.sangyan.easyfood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sangyan.easyfood.model.MealsByCountryList
import com.sangyan.easyfood.adapter.MealsByCountryName
import com.sangyan.easyfood.api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CountryMealsViewModel : ViewModel() {
    private  var countryLiveData  = MutableLiveData<List<MealsByCountryName>>()
    fun getIndianMeals() {
        RetrofitInstance.api.getMealsByCountryName("Indian").enqueue(object  : Callback<MealsByCountryList>{
            override fun onResponse(
                call: Call<MealsByCountryList>,
                response: Response<MealsByCountryList>
            ) {
                if (response.body()!=null){
                    countryLiveData.value = response.body()!!.meals
                }
                else {
                    return
                }
            }

            override fun onFailure(call: Call<MealsByCountryList>, t: Throwable) {
                 Log.d("Indian Food Fragment",t.message.toString() )
            }

        })
    }
    fun countryItemLiveData()  : LiveData<List<MealsByCountryName>>{
         return countryLiveData
    }
}