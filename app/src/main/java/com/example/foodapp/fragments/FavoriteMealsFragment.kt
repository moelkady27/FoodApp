package com.example.foodapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.activities.MainActivity
import com.example.foodapp.adapter.FavoriteMealsAdapter
import com.example.foodapp.adapter.MostPopularAdapter
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_favorite_meals.fav_rec_view
import kotlinx.android.synthetic.main.fragment_home.rec_view_meals_popular

class FavoriteMealsFragment : Fragment() {

    private lateinit var favoriteMvvm : HomeViewModel

    private lateinit var adapter: FavoriteMealsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_favorite_meals,
            container,
            false
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteMvvm = (activity as MainActivity).viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeFavorites()
        showRecycleView()
        deleteItem()

    }



    private fun observeFavorites() {
        favoriteMvvm.observeFavoriteMealsLiveData().observe(viewLifecycleOwner , Observer {  meals ->
            adapter.setFavoriteMealsList(favoriteMeals = meals as ArrayList<Meal>)
        })
    }

    private fun showRecycleView() {
        adapter = FavoriteMealsAdapter()
        fav_rec_view.apply {
            layoutManager = GridLayoutManager(context, 2 , GridLayoutManager.VERTICAL, false)
            adapter = this@FavoriteMealsFragment.adapter
        }
    }

    private fun deleteItem() {
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val mealToDelete = adapter.getMealAt(position)
                favoriteMvvm.deleteMeal(mealToDelete)

                Snackbar.make(
                    requireView(),
                    "Meal was deleted",
                    Snackbar.LENGTH_LONG
                ).apply {
                    setAction("Undo") {
                        favoriteMvvm.insertMeal(mealToDelete)
                    }
                        .show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(fav_rec_view)
        }

    }
}