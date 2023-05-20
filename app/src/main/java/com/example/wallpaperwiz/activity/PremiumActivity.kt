package com.example.wallpaperwiz.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wallpaperwiz.R
import com.example.wallpaperwiz.adapter.PremiumWallpaperAdapter
import com.example.wallpaperwiz.adapter.WallpaperAdapter
import com.example.wallpaperwiz.databinding.ActivityCategoriesBinding
import com.example.wallpaperwiz.databinding.ActivityPremiumBinding
import com.example.wallpaperwiz.helper.Utils
import com.example.wallpaperwiz.model.CategoriesModel
import com.example.wallpaperwiz.model.PremiumModel
import com.google.firebase.firestore.FirebaseFirestore

class PremiumActivity : AppCompatActivity() {

    lateinit var binding: ActivityPremiumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPremiumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Utils.blackIconStatusBar(this, R.color.black)
        initView()
    }

    private fun initView() {

        val db = FirebaseFirestore.getInstance()
        val uid = intent.getStringExtra("uid")
        val name = intent.getStringExtra("name")


        db.collection("premium").document(uid!!).collection("wallpaper").addSnapshotListener { value, error ->
            val list = arrayListOf<PremiumModel>()
            val data = value?.toObjects(PremiumModel::class.java)
            list.addAll(data!!)

            binding.tvPremiumName.text = name
            binding.tvWallpaperAvailable.text="${list.size} wallpapers available"

            binding.rvWallpaper.setHasFixedSize(true)
            binding.rvWallpaper.layoutManager = StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL)
            binding.rvWallpaper.adapter = PremiumWallpaperAdapter(this,list)

        }
    }
}