package com.example.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.activities.CategoryActivity
import com.example.foodapp.activities.MealActivity
import com.example.foodapp.adapter.CategoriesAdapter
import com.example.foodapp.adapter.MostPopularAdapter
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealsCategory
import com.example.foodapp.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.cardView
import kotlinx.android.synthetic.main.fragment_home.img_random_meal
import kotlinx.android.synthetic.main.fragment_home.rec_view_meals_popular
import kotlinx.android.synthetic.main.fragment_home.recycler_view

class HomeFragment : Fragment() {

    private lateinit var homeMvvm : HomeViewModel
    private lateinit var randomMeal: Meal

    private lateinit var adapter: MostPopularAdapter

    private lateinit var adapter1: CategoriesAdapter

    companion object{
        const val MEAL_ID = "com.example.foodapp.fragments.idMeal"
        const val MEAL_NAME = "com.example.foodapp.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.foodapp.fragments.thumbMeal"

        const val CATEGORY_NAME = "com.example.foodapp.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_home,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeMvvm.getRandomMeals()
        observeRandomMeal()
        onRandomMealClicked()

        homeMvvm.getPopularItems()
        observePopularItems()
        popularItemsRecycleView()

        onRandomMealClick()

        categoriesItemsRecycleView()
        homeMvvm.getCategories()
        observeCategoriesItem()

        onCategoryMealClick()
    }

    private fun observeRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner , object : Observer<Meal> {
            override fun onChanged(value: Meal) {
                Glide.with(this@HomeFragment)
                    .load(value.strMealThumb)
                    .into(img_random_meal)

                this@HomeFragment.randomMeal = value
            }
        })
    }

    private fun onRandomMealClicked() {
        cardView.setOnClickListener {
            val intent = Intent(activity , MealActivity::class.java)

            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)

            startActivity(intent)
        }
    }

    private fun observePopularItems() {
        homeMvvm.observePopularItemsLiveData().observe(viewLifecycleOwner
        ) { mealList->
            adapter.setCategoryList(categoryList = mealList as ArrayList<MealsCategory>)
        }
    }

    private fun popularItemsRecycleView() {
        adapter = MostPopularAdapter()
        rec_view_meals_popular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = this@HomeFragment.adapter
        }
    }

    private fun onRandomMealClick() {
        adapter.onItemClick = {meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeCategoriesItem() {
        homeMvvm.observeCategoriesLiveData().observe(viewLifecycleOwner
        ) { categories->
            adapter1.setCategory(categoriesList = categories as ArrayList<Category>)
        }
    }

    private fun categoriesItemsRecycleView() {
        adapter1 = CategoriesAdapter()
        recycler_view.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = this@HomeFragment.adapter1
        }
    }

    private fun onCategoryMealClick() {
        adapter1.onItemClick = {category ->
            val intent = Intent(activity, CategoryActivity::class.java)
            intent.putExtra(CATEGORY_NAME , category.strCategory)
            startActivity(intent)

//            Log.e("CATEGORY_NAME is : " , category.strCategory)
        }
    }
}