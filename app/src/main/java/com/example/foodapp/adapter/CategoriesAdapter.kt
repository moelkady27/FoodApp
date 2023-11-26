package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.MealsCategory
import kotlinx.android.synthetic.main.category_item.view.img_category
import kotlinx.android.synthetic.main.category_item.view.tvCategoryName
import kotlinx.android.synthetic.main.populat_items.view.img_meal

class CategoriesAdapter() : RecyclerView.Adapter<CategoriesAdapter.MyViewHolder>() {

    private var list : List<Category> = ArrayList()

    lateinit var onItemClick : ((Category) -> Unit)

    fun setCategory(categoriesList: List<Category>){
        this.list = categoriesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item , parent ,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val x = list[position]

        Glide.with(holder.itemView)
            .load(x.strCategoryThumb)
            .into(holder.itemView.img_category)

        holder.itemView.tvCategoryName.text = x.strCategory

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(x)
        }
    }


    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view)

}