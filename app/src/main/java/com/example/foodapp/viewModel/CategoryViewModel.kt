package com.example.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealCategorysResponse
import com.example.foodapp.pojo.MealsCategory
import com.example.foodapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel : ViewModel() {

    private var mealsLiveData = MutableLiveData<List<MealsCategory>>()

    fun getMealsByCategory(categoryName : String){
        RetrofitClient.instance.getMealsByCategory(categoryName)
            .enqueue(object : Callback<MealCategorysResponse>{
                override fun onResponse(
                    call: Call<MealCategorysResponse>,
                    response: Response<MealCategorysResponse>
                ) {
                    response.body().let {mealsList ->
                        mealsLiveData.postValue(mealsList!!.meals)
                    }
                }

                override fun onFailure(call: Call<MealCategorysResponse>, t: Throwable) {
                    Log.e("Category" , t.message.toString())
                }

            })
    }

    fun observeCategoryMealLiveData() : LiveData<List<MealsCategory>> {
        return mealsLiveData
    }
}