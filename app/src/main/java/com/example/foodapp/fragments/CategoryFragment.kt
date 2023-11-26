package com.example.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.R
import com.example.foodapp.activities.CategoryActivity
import com.example.foodapp.activities.MainActivity
import com.example.foodapp.adapter.CategoriesAdapter
import com.example.foodapp.adapter.CategoryMealsAdapter
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealsCategory
import com.example.foodapp.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_category.favorite_recycler_view

class CategoryFragment : Fragment() {

    private lateinit var categoryMvvm: HomeViewModel
    private lateinit var adapter: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_category,
            container,
            false
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryMvvm = (activity as MainActivity).viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRecyclerView()
        observeCategories()

        onCategoryMealClick()
    }

    private fun showRecyclerView() {
        adapter = CategoriesAdapter()
        favorite_recycler_view.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = this@CategoryFragment.adapter
        }
    }

    private fun observeCategories() {
        categoryMvvm.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories ->
            adapter.setCategory(categoriesList = categories as ArrayList<Category>)
        })
    }

    private fun onCategoryMealClick() {
        adapter.onItemClick = {category ->
            val intent = Intent(activity, CategoryActivity::class.java)
            intent.putExtra(HomeFragment.CATEGORY_NAME, category.strCategory)
            startActivity(intent)

            Log.e("CATEGORY_NAME is : " , category.strCategory)
        }
    }
}