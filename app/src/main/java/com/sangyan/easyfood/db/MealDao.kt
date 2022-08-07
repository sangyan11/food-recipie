package com.sangyan.easyfood.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sangyan.easyfood.model.Meal

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal : Meal)
      @Delete
    suspend fun  delete(meal : Meal)

    @Query("Select * from mealInformation")
    fun getAllMeals() : LiveData<List<Meal>>

}