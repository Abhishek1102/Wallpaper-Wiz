package com.example.wallpaperwiz.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import com.example.wallpaperwiz.R
import com.example.wallpaperwiz.databinding.ActivityMainBinding
import com.example.wallpaperwiz.fragment.DashboardFragment
import com.example.wallpaperwiz.fragment.DownloadsFragment
import com.example.wallpaperwiz.helper.AppConstant

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addfragment(DashboardFragment(),"DashboardFragment")

        initView()

    }

    private fun initView() {

        //use this as context in progressdialog instead of using application context
        AppConstant.showProgressDialog(this)
        Handler().postDelayed({
            AppConstant.hideProgressDialog()
        },1500)

        binding.ivDownloads.setOnClickListener {
            addfragment(DownloadsFragment(),"DownloadsFragment")
        }

        binding.ivHome.setOnClickListener {
            addfragment(DashboardFragment(),"DashboardFragment")
        }

    }

    private fun addfragment(fragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_dashboard, fragment, tag)
        fragmentTransaction.commit()
    }
}