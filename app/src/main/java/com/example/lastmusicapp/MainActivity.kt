package com.example.lastmusicapp


import MainListAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.lastmusicapp.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.example.lastmusicapp.models.ParentModel


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mainListAdapter: MainListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getCategories()
        binding.menue.setOnClickListener {

        }
    }

    override fun onResume() {
        super.onResume()
        showPlayerView()
    }
    fun showPlayerView(){
        binding.playerView.setOnClickListener {
            startActivity(Intent(this,PlayerActivity::class.java))
        }
        MyExoplayer.getCurrentSong()?.let {
            binding.playerView.visibility = View.VISIBLE
            binding.songTitleTextView.text = " " + it.title
            Glide.with(binding.songCoverImageView).load(it.coverUrl)
                .apply(
                    RequestOptions().transform(RoundedCorners(32))
                ).into(binding.songCoverImageView)
        } ?: run{
            binding.playerView.visibility = View.GONE
        }
    }

    // Categories
    fun getCategories(){
        FirebaseFirestore.getInstance().collection("category")
            .get().addOnSuccessListener {
                val categoryList=it.toObjects(ParentModel::class.java)
                Log.e("search_Category","$categoryList")

                setupCategoryRecyclerView(categoryList)

            }.addOnFailureListener {
                Log.e("search_Category","$it")
            }
    }

    fun setupCategoryRecyclerView(categoryList : List<ParentModel>){
        mainListAdapter = MainListAdapter(categoryList,this)
        binding.categoriesItemRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.categoriesItemRecyclerView.adapter = mainListAdapter
    }



}