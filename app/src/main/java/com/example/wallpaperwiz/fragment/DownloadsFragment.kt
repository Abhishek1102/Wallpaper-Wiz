package com.example.wallpaperwiz.fragment

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wallpaperwiz.adapter.DownloadAdapter
import com.example.wallpaperwiz.databinding.FragmentDownloadsBinding
import java.io.File

class DownloadsFragment : Fragment() {

    lateinit var binding: FragmentDownloadsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDownloadsBinding.inflate(layoutInflater, container, false)

        initView()

        return binding.root
    }

    private fun initView() {
        val allFiles:Array<File>
        val imageList = arrayListOf<String>()

        val targetPath=Environment.getExternalStorageDirectory().absolutePath+"/Pictures/Wallpaper_Wiz"

        val targetFile=File(targetPath)
        allFiles = targetFile.listFiles()!!

        for (data in allFiles){
            imageList.add(data.absolutePath)
        }

        binding.tvWallpaperDownloaded.text = "${imageList.size} wallpapers downloaded"
        binding.rvDownloads.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        binding.rvDownloads.adapter = DownloadAdapter(requireContext(),imageList)
    }

}