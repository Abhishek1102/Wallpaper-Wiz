package com.example.wallpaperwiz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaperwiz.R
import com.example.wallpaperwiz.model.BomModel

class BomAdapter(val context: Context,val list: ArrayList<BomModel>):RecyclerView.Adapter<BomAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_bom,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(list[position].link).into(holder.iv_bom)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val iv_bom:ImageView = itemView.findViewById(R.id.iv_bom)
    }
}