package com.example.foodapp.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.db.MealsDatabase
import com.example.foodapp.fragments.HomeFragment
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewModel.MealViewModel
import com.example.foodapp.viewModel.MealViewModelFactory
import kotlinx.android.synthetic.main.activity_meal.btn_add_to_favourites
import kotlinx.android.synthetic.main.activity_meal.collapsing_toolbar
import kotlinx.android.synthetic.main.activity_meal.img_meal_detail
import kotlinx.android.synthetic.main.activity_meal.img_youtube
import kotlinx.android.synthetic.main.activity_meal.progressBar
import kotlinx.android.synthetic.main.activity_meal.tv_areaInfo
import kotlinx.android.synthetic.main.activity_meal.tv_categoryInfo
import kotlinx.android.synthetic.main.activity_meal.tv_instructions

class MealActivity : AppCompatActivity() {

    private lateinit var mealId : String
    private lateinit var mealStr : String
    private lateinit var mealThumb : String
    private lateinit var mealMvvm : MealViewModel
    private lateinit var ytUrl : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)

        showLoading()

        getMealInfoFrom()

        setUpViewWithMealInformation()

//        mealMvvm = ViewModelProviders.of(this).get(MealViewModel::class.java)

//        room
        val mealsDatabase = MealsDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealsDatabase)
        mealMvvm = ViewModelProvider(this , viewModelFactory).get(MealViewModel::class.java)
//

        mealMvvm.getMealDetails(mealId)

        observeMealDetails()

        onFavoriteClick()

        img_youtube.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(ytUrl)))
        }
    }

    private fun getMealInfoFrom() {
        val tempIntent = intent

        this.mealId = tempIntent.getStringExtra(HomeFragment.MEAL_ID)!!
        this.mealStr = tempIntent.getStringExtra(HomeFragment.MEAL_NAME)!!
        this.mealThumb = tempIntent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun setUpViewWithMealInformation() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(img_meal_detail)

        collapsing_toolbar.title = mealStr
    }

    private var mealToSave: Meal? = null
    private fun observeMealDetails() {
        mealMvvm.observeMealDetailsLiveData().observe(this , object : Observer<Meal>{
            override fun onChanged(value: Meal) {

                stopLoading()

                val meal = value

                mealToSave = meal

                tv_categoryInfo.text = "Category : ${meal.strCategory}"
                tv_areaInfo.text = "Area : ${meal.strArea}"
                tv_instructions.text = meal.strInstructions
                ytUrl = meal.strYoutube!!
            }

        })
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        btn_add_to_favourites.visibility = View.GONE
        img_youtube.visibility = View.INVISIBLE
    }


    private fun stopLoading() {
        progressBar.visibility = View.INVISIBLE
        btn_add_to_favourites.visibility = View.VISIBLE

        img_youtube.visibility = View.VISIBLE

    }

    private fun onFavoriteClick() {
        btn_add_to_favourites.setOnClickListener {
            mealToSave.let {
                mealMvvm.insertMeal(it!!)
                Toast.makeText(this , "Meal Saved" , Toast.LENGTH_LONG).show()
            }
        }
    }
}