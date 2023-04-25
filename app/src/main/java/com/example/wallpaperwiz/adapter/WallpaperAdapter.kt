package com.example.wallpaperwiz.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaperwiz.R
import com.example.wallpaperwiz.activity.FinalWallpaperActivity
import com.example.wallpaperwiz.model.BomModel
import com.example.wallpaperwiz.model.CategoriesModel

class WallpaperAdapter(val context: Context, val list: ArrayList<CategoriesModel>) :
    RecyclerView.Adapter<WallpaperAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_wallpaper,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(list[position].link).into(holder.iv_wallpaper)
        holder.itemView.setOnClickListener {
            val i = Intent(context,FinalWallpaperActivity::class.java)
            i.putExtra("link",list[position].link)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv_wallpaper:ImageView = itemView.findViewById(R.id.iv_wallpaper)
    }
}