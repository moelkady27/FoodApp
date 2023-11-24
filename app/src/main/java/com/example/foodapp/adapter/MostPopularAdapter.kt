package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.pojo.MealsCategory
import kotlinx.android.synthetic.main.populat_items.view.img_meal

class MostPopularAdapter() : RecyclerView.Adapter<MostPopularAdapter.MyViewHolder>() {

    private var list : List<MealsCategory> = ArrayList()

    lateinit var onItemClick : ((MealsCategory) -> Unit)

    fun setCategoryList(categoryList: List<MealsCategory>){
        this.list = categoryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.populat_items ,
            parent ,
            false
        )
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val x = list[position]

        Glide.with(holder.itemView)
            .load(x.strMealThumb)
            .into(holder.itemView.img_meal)


        holder.itemView.setOnClickListener {
            onItemClick.invoke(x)
        }
    }

    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view)

}