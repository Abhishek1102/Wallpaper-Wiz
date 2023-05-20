package com.example.wallpaperwiz.activity

import android.app.Dialog
import android.app.WallpaperManager
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.wallpaperwiz.R
import com.example.wallpaperwiz.databinding.ActivityFinalPremiumWallpaperBinding
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File
import java.io.OutputStream
import java.lang.Exception
import java.net.URL
import java.util.*

class FInalPremiumWallpaperActivity : AppCompatActivity(),PaymentResultListener {

    lateinit var binding:ActivityFinalPremiumWallpaperBinding
    lateinit var urlImage:URL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalPremiumWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {

        val url = intent.getStringExtra("link")
        urlImage = URL(url)

        Glide.with(this).load(url).into(binding.ivPremiumWallpaper)

        binding.cardDownloadWallpaper.setOnClickListener {

            //opening payment dialogbox
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.custom_payment_dialog)
            val title = dialog.findViewById<TextView>(R.id.txt_dialog_title)
            val msg = dialog.findViewById<TextView>(R.id.txt_dialog_msg)
            val yes = dialog.findViewById<TextView>(R.id.txt_positive)
            val no = dialog.findViewById<TextView>(R.id.txt_negative)

            title.setText("Premium")
            msg.setText("This is a premium wallpaper, to download this wallpaper you must pay for it!")
            yes.setText("Yes")
            no.setText("No")

            yes.setOnClickListener {
                dialog.dismiss()
                //sending values as json object
                val co = Checkout()

                try {
                    val options = JSONObject()
                    options.put("name","Wallpaper Wiz")
                    options.put("description","Providing you hd wallpaper to download")
                    options.put("image","https://firebasestorage.googleapis.com/v0/b/wallpaper-wiz.appspot.com/o/crystal-ball.png?alt=media&token=4b2a4c04-84de-4297-98ce-d39cf84f18c5")
                    options.put("theme.color","#3399CC")
                    options.put("currency","INR")
                    options.put("amount","2000")

                    val prefill = JSONObject()
                    prefill.put("email","")
                    prefill.put("contact","")

                    options.put("prefill",prefill)
                    co.open(this,options)
                }
                catch (e:Exception){
                    Toast.makeText(applicationContext, "Error in payment: ${e.message}", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }

            }

            no.setOnClickListener {
                dialog.dismiss()
            }

            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()


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

    override fun onPaymentSuccess(p0: String?) {
        val result: kotlinx.coroutines.Deferred<Bitmap?> = GlobalScope.async {
            urlImage.toBitmap()
        }
        GlobalScope.launch (Dispatchers.Main){
            saveImage(result.await())
        }
    }

    override fun onPaymentError(p0: Int, p1: String?) {

    }
}