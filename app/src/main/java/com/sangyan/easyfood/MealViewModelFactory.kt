package com.sangyan.easyfood

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sangyan.easyfood.db.MealDatabase
import com.sangyan.easyfood.viewmodel.MealViewModel

class MealViewModelFactory(
    private val mealDatabase: MealDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(mealDatabase) as T
    }
}