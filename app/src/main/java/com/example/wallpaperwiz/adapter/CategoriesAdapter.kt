package com.example.wallpaperwiz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaperwiz.R
import com.example.wallpaperwiz.model.CategoriesModel

class CategoriesAdapter(val context: Context,val list: ArrayList<CategoriesModel>):RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_categories,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_categoriesName.text = list[position].name
        Glide.with(context).load(list[position].link).into(holder.iv_categoriesBg)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val tv_categoriesName:TextView = itemView.findViewById(R.id.tv_categoriesName)
        val iv_categoriesBg:ImageView = itemView.findViewById(R.id.iv_categoriesBg)
    }
}