package com.example.wallpaperwiz.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wallpaperwiz.R
import com.example.wallpaperwiz.adapter.BomAdapter
import com.example.wallpaperwiz.adapter.CategoriesAdapter
import com.example.wallpaperwiz.adapter.TctAdapter
import com.example.wallpaperwiz.databinding.FragmentDashboardBinding
import com.example.wallpaperwiz.model.BomModel
import com.example.wallpaperwiz.model.CategoriesModel
import com.example.wallpaperwiz.model.TctModel
import com.google.firebase.firestore.FirebaseFirestore

class DashboardFragment : Fragment() {

    lateinit var binding: FragmentDashboardBinding
    lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)


        initView()
        bestOfTheMonth()
        theColorTone()
        categories()

        return binding.root
    }

    private fun initView() {
        db = FirebaseFirestore.getInstance()

    }

    private fun bestOfTheMonth() {
        db.collection("bestofthemonth").addSnapshotListener { value, error ->
            val list_bom = arrayListOf<BomModel>()
            val data = value!!.toObjects(BomModel::class.java)
            list_bom.addAll(data)

            binding.rvBom.layoutManager= LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            binding.rvBom.adapter = BomAdapter(requireContext(),list_bom)

        }
    }

    private fun theColorTone() {
        db.collection("thecolortone").addSnapshotListener { value, error ->
            val list_tct = arrayListOf<TctModel>()
            val data = value!!.toObjects(TctModel::class.java)
            list_tct.addAll(data)

            binding.rvTct.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            binding.rvTct.adapter = TctAdapter(requireContext(),list_tct)

        }
    }

    private fun categories() {
        db.collection("categories").addSnapshotListener { value, error ->
        val list_categories = arrayListOf<CategoriesModel>()
            val data = value!!.toObjects(CategoriesModel::class.java)
            list_categories.addAll(data)

            for (i in list_categories)
            {
                Log.e("@","onCreateView: $i")
            }
            binding.rvCategories.layoutManager = GridLayoutManager(context,2)
            binding.rvCategories.adapter = CategoriesAdapter(requireContext(),list_categories)
        }
    }

}