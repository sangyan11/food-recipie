package com.sangyan.easyfood

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sangyan.easyfood.db.MealDatabase
import com.sangyan.easyfood.viewmodel.HomeViewModel

class HomeViewModelFactory(
    private val mealDatabase: MealDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(mealDatabase) as T
    }
}