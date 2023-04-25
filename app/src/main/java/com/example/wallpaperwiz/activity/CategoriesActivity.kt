package com.example.wallpaperwiz.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wallpaperwiz.adapter.WallpaperAdapter
import com.example.wallpaperwiz.databinding.ActivityCategoriesBinding
import com.example.wallpaperwiz.model.CategoriesModel
import com.google.firebase.firestore.FirebaseFirestore

class CategoriesActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {

        val db = FirebaseFirestore.getInstance()
        val uid = intent.getStringExtra("uid")
        val name = intent.getStringExtra("name")


        db.collection("categories").document(uid!!).collection("wallpaper").addSnapshotListener { value, error ->
            val list = arrayListOf<CategoriesModel>()
            val data = value?.toObjects(CategoriesModel::class.java)
            list.addAll(data!!)

            binding.tvCategoriesName.text = name
            binding.tvWallpaperAvailable.text="${list.size} wallpapers available"

            binding.rvWallpaper.setHasFixedSize(true)
            binding.rvWallpaper.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            binding.rvWallpaper.adapter = WallpaperAdapter(this,list)

        }
    }
}