package com.example.wallpaperwiz.activity

import android.app.WallpaperManager
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.wallpaperwiz.R
import com.example.wallpaperwiz.databinding.ActivityFinalWallpaperBinding
import com.google.firebase.inject.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.io.OutputStream
import java.lang.Exception
import java.net.URL
import java.util.*

class FinalWallpaperActivity : AppCompatActivity() {

    lateinit var binding:ActivityFinalWallpaperBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {

        val url = intent.getStringExtra("link")
        val urlImage = URL(url)

        Glide.with(this).load(url).into(binding.ivWallpaper)

        binding.cardDownloadWallpaper.setOnClickListener {

            val result: kotlinx.coroutines.Deferred<Bitmap?> = GlobalScope.async {
                urlImage.toBitmap()
            }
            GlobalScope.launch (Dispatchers.Main){
                saveImage(result.await())
            }

        }

        binding.cardSetWallpaper.setOnClickListener {
            val result: kotlinx.coroutines.Deferred<Bitmap?> = GlobalScope.async {
                urlImage.toBitmap()
            }
            GlobalScope.launch (Dispatchers.Main){
                val wallpaperManager = WallpaperManager.getInstance(applicationContext)
                wallpaperManager.setBitmap(result.await())
            }
        }

    }

    fun URL.toBitmap():Bitmap?{
        return try{
            BitmapFactory.decodeStream(openStream())
        }catch (e:Exception){
            null
        }
    }

    private fun saveImage(image:Bitmap?){

        //making random variable by adding two random variable
        //520985 and 646564 are just lenght of random integer you can also write 10 as two digit lengh, you can give any lenght format data like 123(three digit)
        val random1 = Random().nextInt(520985)
        val random2 = Random().nextInt(646564)

        val name = "WALLPAPER_WIZ-${random1 + random2}"

        val data: OutputStream
        try {

            val resolver = contentResolver
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "$name.jpg")
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,Environment.DIRECTORY_PICTURES + File.separator + "Wallpaper_Wiz")

            val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues)
            data = resolver.openOutputStream(Objects.requireNonNull(imageUri)!!)!!
            image?.compress(Bitmap.CompressFormat.JPEG,100,data)
            Objects.requireNonNull<OutputStream?>(data)
            Toast.makeText(applicationContext, "Image Saved", Toast.LENGTH_SHORT).show()

        }catch (e : Exception){
            Toast.makeText(applicationContext, "Unable to save Image", Toast.LENGTH_SHORT).show()
        }

    }
}