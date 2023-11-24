package com.example.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.CategoryResponse
import com.example.foodapp.pojo.MealsCategory
import com.example.foodapp.pojo.MealCategorysResponse
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.RandomMealResponse
import com.example.foodapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemLiveData = MutableLiveData<List<MealsCategory>>()
    private var categoryLiveData = MutableLiveData<List<Category>>()

    fun getRandomMeals(){
        RetrofitClient.instance.getRandomMeal()
            .enqueue(object : Callback<RandomMealResponse> {
                override fun onResponse(
                    call: Call<RandomMealResponse>,
                    response: Response<RandomMealResponse>
                ) {
                    if (response.body() != null){
                        val randomMeal : Meal = response.body()!!.meals[0]
                        randomMealLiveData.value = randomMeal
                    }
                    else{
                        return
                    }
                }

                override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
                    Log.e("Home Fragment" , t.message.toString())
                }

            })
    }

     fun observeRandomMealLiveData() : LiveData<Meal>{
        return randomMealLiveData
    }

    fun getPopularItems(){
        RetrofitClient.instance.getPopularItems("Seafood")
            .enqueue(object : Callback<MealCategorysResponse>{
                override fun onResponse(
                    call: Call<MealCategorysResponse>,
                    response: Response<MealCategorysResponse>
                ) {
                    if (response.body() != null){
                        popularItemLiveData.value = response.body()!!.meals
                    }
                    else{
                        return
                    }
                }

                override fun onFailure(call: Call<MealCategorysResponse>, t: Throwable) {
                    Log.e("Home Fragment" , t.message.toString())
                }

            })
    }

    fun observePopularItemsLiveData() : LiveData<List<MealsCategory>>{
        return popularItemLiveData
    }

    fun getCategories(){
        RetrofitClient.instance.getCategories()
            .enqueue(object : Callback<CategoryResponse>{
                override fun onResponse(
                    call: Call<CategoryResponse>,
                    response: Response<CategoryResponse>
                ) {
                    if (response.body() != null){
                        categoryLiveData.value = response.body()!!.categories
                    }
                    else{
                        return
                    }
                }

                override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                    Log.e("Home Fragment" , t.message.toString())
                }

            })
    }

    fun observeCategoriesLiveData() : LiveData<List<Category>>{
        return categoryLiveData
    }
}