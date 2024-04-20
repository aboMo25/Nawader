package com.example.lastmusicapp


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.lastmusicapp.adapters.SubListAdapter
import com.example.lastmusicapp.databinding.ParentItemBinding
import com.example.lastmusicapp.models.ParentModel

class SongsListActivity : AppCompatActivity() {
companion object{
    lateinit var category : ParentModel
}
    lateinit var  binding: ParentItemBinding

    lateinit var songsListAdapter: SubListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ParentItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nameTextView.text= category.name
        // Get image From firebase
        Glide.with(binding.coverImageView).load(category.coverUrl)
            .apply(
                RequestOptions().transform(RoundedCorners(32))
            )
            .into(binding.coverImageView)

        setupSongsListRecyclerView()
    }
    fun setupSongsListRecyclerView(){
        songsListAdapter = SubListAdapter(category.songs)
        binding.songsListRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.songsListRecyclerView.adapter = songsListAdapter
    }

}

