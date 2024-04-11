package com.example.lastmusicapp


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.lastmusicapp.adapters.SongListAdapter
import com.example.lastmusicapp.databinding.ActivityMainBinding
import com.example.lastmusicapp.databinding.ActivitySongListBinding
import com.example.lastmusicapp.models.CategoryModel

class SongListActivity : AppCompatActivity() {

    companion object{
        lateinit var category : CategoryModel
    }

    lateinit var  binding: ActivitySongListBinding
//    lateinit var mainBinding:ActivityMainBinding
    lateinit var songsListAdapter: SongListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivitySongListBinding.inflate(layoutInflater)
        setContentView(binding.root)
//
//        mainBinding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(mainBinding.root)

        binding.nameTextView.text= category.name

        Glide.with(binding.coverImageView).load(category.coverUrl)
            .apply(
                RequestOptions().transform(RoundedCorners(32))
            )
            .into(binding.coverImageView)

        setupSongsListRecyclerView()
    }
    fun setupSongsListRecyclerView(){
        songsListAdapter = SongListAdapter(category.songs)
//        mainBinding.songsListRecyclerView2.layoutManager = LinearLayoutManager(this)
//        mainBinding.songsListRecyclerView2.adapter = songsListAdapter
        binding.songsListRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.songsListRecyclerView.adapter = songsListAdapter
    }

}

