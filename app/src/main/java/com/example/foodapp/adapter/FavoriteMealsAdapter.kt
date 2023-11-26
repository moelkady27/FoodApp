package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealsCategory
import kotlinx.android.synthetic.main.meal_card.view.img_meal1
import kotlinx.android.synthetic.main.meal_card.view.tv_meal_name

class FavoriteMealsAdapter : RecyclerView.Adapter<FavoriteMealsAdapter.MyViewHolder>() {

    private var list : List<Meal> = ArrayList()

    fun setFavoriteMealsList(favoriteMeals: List<Meal>) {
        this.list = favoriteMeals
        notifyDataSetChanged()
    }

//    private val diffUtil = object : DiffUtil.ItemCallback<Meal>(){
//        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
//            return oldItem.idMeal == newItem.idMeal
//        }
//
//        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
//            return oldItem == newItem
//        }
//
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.meal_card ,
            parent ,
            false
        )
        return FavoriteMealsAdapter.MyViewHolder(view)
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


    fun getMealAt(position: Int): Meal {
        return list[position]
    }

    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view)

}