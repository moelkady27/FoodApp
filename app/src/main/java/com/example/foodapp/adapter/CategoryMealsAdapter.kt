package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.pojo.MealsCategory
import kotlinx.android.synthetic.main.meal_card.view.img_meal1
import kotlinx.android.synthetic.main.meal_card.view.tv_meal_name

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.MyViewHolder>() {

    private var list : List<MealsCategory> = ArrayList()

    fun setCategoryList(mealCategoryList: List<MealsCategory>){
        this.list = mealCategoryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.meal_card ,
            parent ,
            false
        )
        return CategoryMealsAdapter.MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val x = list[position]

        Glide.with(holder.itemView)
            .load(x.strMealThumb)
            .into(holder.itemView.img_meal1)

        holder.itemView.tv_meal_name.text = x.strMeal

    }


    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view)

}