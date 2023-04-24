package com.example.wallpaperwiz.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperwiz.R
import com.example.wallpaperwiz.model.TctModel

class TctAdapter(val context: Context,val list:ArrayList<TctModel>):RecyclerView.Adapter<TctAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_tct,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val color = list[position].color
        holder.lv_tct.setBackgroundColor(Color.parseColor(color))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val lv_tct:LinearLayout = itemView.findViewById(R.id.lv_tct)
    }
}