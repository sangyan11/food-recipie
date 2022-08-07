package com.sangyan.easyfood.ui.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.sangyan.easyfood.MealViewModelFactory
import com.sangyan.easyfood.R
import com.sangyan.easyfood.databinding.ActivityMealBinding
import com.sangyan.easyfood.db.MealDatabase
import com.sangyan.easyfood.model.Meal
import com.sangyan.easyfood.ui.fragment.HomeFragment
import com.sangyan.easyfood.viewmodel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMealBinding
    private lateinit var mealId : String
    private lateinit var mealName : String
    private lateinit var mealThumb : String
    private lateinit var mealMVVM : MealViewModel
    private lateinit var youtubeLink : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getMealInformationFromIntent()
        getMealsFromCategoryIntent()
        setInformationViews()
        val meadDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(meadDatabase)
        mealMVVM = ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]
        loadingCase()
        mealMVVM.getMealDetails(mealId)
        observeMealDetailsLiveData()
        onYoutubeImageClick()
        onFavoriteClicked()
    }

    private fun onFavoriteClicked() {
        binding.btnFav.setOnClickListener {
          mealToSave?.let {
              mealMVVM.insertMeal(it)
              Toast.makeText(this ,"Added to Favorites",Toast.LENGTH_SHORT).show()
          }
        }
    }

    private fun onYoutubeImageClick() {
        binding.youtubeBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }
    private var mealToSave : Meal?= null
    private fun observeMealDetailsLiveData() {
        mealMVVM.observeMealDetailsLiveData().observe(this,object : Observer<Meal>{
            override fun onChanged(t: Meal?) {
                onResponseCase()
                val meal = t
                mealToSave = meal
                binding.tvCategory.text = "Category : ${meal!!.strCategory}"
                binding.tvArea.text = "Area : ${meal.strArea}"
                binding.tvInstructionsSteps.text = meal.strInstructions
                youtubeLink = meal.strYoutube!!
            }

        })
    }

    private fun setInformationViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolBar.title = mealName
        binding.collapsingToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolBar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
       val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_Name)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }
    private fun getMealsFromCategoryIntent() {
        mealId = intent.getStringExtra(CategoryMealsActivity.MEAL_ID)!!
        mealName = intent.getStringExtra(CategoryMealsActivity.MEAL_Name)!!
        mealThumb = intent.getStringExtra(CategoryMealsActivity.MEAL_THUMB)!!
    }
    private fun loadingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnFav.visibility = View.INVISIBLE
        binding.instructions.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.youtubeBtn.visibility = View.INVISIBLE
    }
    fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnFav.visibility = View.VISIBLE
        binding.instructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.youtubeBtn.visibility = View.VISIBLE
    }
}