package com.sangyan.easyfood.db

import android.content.Context
import androidx.room.*
import com.sangyan.easyfood.model.Meal

@Database(entities = [Meal::class], version = 1, exportSchema = false)
@TypeConverters(MealTypeConvertor::class)
abstract class MealDatabase  : RoomDatabase() {
    abstract fun mealDao() : MealDao
    companion object {
        @Volatile
        var INSTANCE : RoomDatabase?=null
        @Synchronized
        fun getInstance(context : Context)  : MealDatabase {
            if (INSTANCE ==null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "meal_db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MealDatabase
        }
    }
}