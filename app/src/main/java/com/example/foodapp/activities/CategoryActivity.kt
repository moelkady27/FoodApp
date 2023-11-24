package com.example.foodapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.R
import com.example.foodapp.adapter.CategoryMealsAdapter
import com.example.foodapp.adapter.MostPopularAdapter
import com.example.foodapp.fragments.HomeFragment
import com.example.foodapp.viewModel.CategoryViewModel
import kotlinx.android.synthetic.main.activity_category.meal_recyclerview
import kotlinx.android.synthetic.main.activity_category.tv_category_count
import kotlinx.android.synthetic.main.fragment_home.rec_view_meals_popular
import kotlinx.android.synthetic.main.fragment_home.tv_category

class CategoryActivity : AppCompatActivity() {

    private lateinit var categoryMvvm : CategoryViewModel

    private lateinit var adapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        categoryMvvm = ViewModelProviders.of(this).get(CategoryViewModel::class.java)

        categoryMvvm.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        getRecycleView()

        getData()
    }

    private fun getData() {
        categoryMvvm.observeCategoryMealLiveData().observe(this
        ) {mealsList ->
            adapter.setCategoryList(mealsList)

            tv_category_count.text = mealsList.size.toString()
        }
    }

    private fun getRecycleView() {
        adapter = CategoryMealsAdapter()
        meal_recyclerview.apply {
            layoutManager = GridLayoutManager(context, 2 ,GridLayoutManager.VERTICAL, false)
            adapter = this@CategoryActivity.adapter
        }
    }

}