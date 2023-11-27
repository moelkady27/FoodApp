package com.example.foodapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.R
import com.example.foodapp.activities.MainActivity
import com.example.foodapp.adapter.FavoriteMealsAdapter
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_search.ed_search
import kotlinx.android.synthetic.main.fragment_search.ic_search
import kotlinx.android.synthetic.main.fragment_search.searched
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    private lateinit var adapter: FavoriteMealsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_search,
            container,
            false
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showRecycleView()
        observeSearch()

        ic_search.setOnClickListener {
            searchMeals()
        }

        observeSearchMealLiveData()

        var searchJob: Job? = null
        ed_search.addTextChangedListener {searchQuery ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.searchMeal(searchQuery.toString())
            }
        }
    }

    private fun showRecycleView() {
        adapter = FavoriteMealsAdapter()
        searched.apply {
            layoutManager = GridLayoutManager(context, 2 , GridLayoutManager.VERTICAL, false)
            adapter = this@SearchFragment.adapter
        }
    }

    private fun observeSearch() {
        viewModel.observeSearchLiveData().observe(viewLifecycleOwner, Observer { mealsList ->
            mealsList?.let {
                adapter.setFavoriteMealsList(favoriteMeals = it as ArrayList<Meal>)
            }
        })
    }

    private fun searchMeals() {
        val searchQuery = ed_search.text.toString()
        if (searchQuery.isNotEmpty()){
            viewModel.searchMeal(searchQuery)
        }
    }

    private fun observeSearchMealLiveData() {
        viewModel.observeSearchLiveData().observe(viewLifecycleOwner, Observer { mealList ->
            mealList?.let {
                if (it is ArrayList<*>) {
                    adapter.setFavoriteMealsList(favoriteMeals = it as ArrayList<Meal>)
                }
            }
        })
    }
}