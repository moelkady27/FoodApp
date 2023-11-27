package com.example.foodapp.retrofit

import com.example.foodapp.pojo.CategoryResponse
import com.example.foodapp.pojo.MealCategorysResponse
import com.example.foodapp.pojo.RandomMealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("random.php")
    fun getRandomMeal(
    ) : Call<RandomMealResponse>

    @GET("lookup.php?")
    fun getMealDetails(
        @Query("i") id: String
    ) : Call<RandomMealResponse>

    @GET("filter.php?")
    fun getPopularItems(
        @Query("c") categoryName: String
    ) : Call<MealCategorysResponse>

    @GET("categories.php")
    fun getCategories(
    ) : Call<CategoryResponse>

    @GET("filter.php")
    fun getMealsByCategory(
        @Query("c") categoryName: String
    ) : Call<MealCategorysResponse>

    @GET("search.php")
    fun searchMeals(
        @Query("s") searchQuery: String
    ) : Call<RandomMealResponse>
}