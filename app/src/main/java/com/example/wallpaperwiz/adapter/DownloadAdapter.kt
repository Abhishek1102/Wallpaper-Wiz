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
import com.example.wallpaperwiz.model.CategoriesModel
import java.util.ArrayList

class DownloadAdapter(val context: Context,val list: ArrayList<String>):RecyclerView.Adapter<DownloadAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_downloads,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(list[position]).into(holder.iv_downloads)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val iv_downloads:ImageView = itemView.findViewById(R.id.iv_downloads)
    }
}