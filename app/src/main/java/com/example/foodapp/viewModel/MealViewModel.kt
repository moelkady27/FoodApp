package com.example.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.RandomMealResponse
import com.example.foodapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel : ViewModel() {

    private var mealDetailsLiveData = MutableLiveData<Meal>()

    fun getMealDetails(id : String){
        RetrofitClient.instance.getMealDetails(id)
            .enqueue(object : Callback<RandomMealResponse>{
                override fun onResponse(
                    call: Call<RandomMealResponse>,
                    response: Response<RandomMealResponse>
                ) {
                    if (response.body() != null){
                        val mealDetails : Meal = response.body()!!.meals[0]
                        mealDetailsLiveData.value = mealDetails
                    }
                    else{
                        return
                    }
                }

                override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
                    Log.e("Meal Activity" , t.message.toString())
                }

            })
    }

    fun observeMealDetailsLiveData() : LiveData<Meal> {
        return mealDetailsLiveData
    }
}