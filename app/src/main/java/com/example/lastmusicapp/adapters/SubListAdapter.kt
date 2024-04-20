package com.example.lastmusicapp.adapters
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.lastmusicapp.MyExoplayer
import com.example.lastmusicapp.PlayerActivity
import com.example.lastmusicapp.databinding.SubListItemBinding
import com.example.lastmusicapp.models.SongModel

import com.google.firebase.firestore.FirebaseFirestore

class SubListAdapter(private  val songIdList : List<String>) :
    RecyclerView.Adapter<SubListAdapter.MyViewHolder>() {
    class MyViewHolder(private val binding: SubListItemBinding) : RecyclerView.ViewHolder(binding.root){
        //bind data with view
        fun bindData(songId : String){
            FirebaseFirestore.getInstance().collection("songs").
            document(songId).get().addOnSuccessListener {
                val song = it.toObject(SongModel::class.java)
                song?.apply {
                    binding.songTitleTextView.text = title
                    binding.songSubtitleTextView.text = subtitle

                    Glide.with(binding.songCoverImageView).load(coverUrl)
                        .apply(
                            RequestOptions().transform(RoundedCorners(32))
                        )
                        .into(binding.songCoverImageView)

                    binding.root.setOnClickListener {
                        MyExoplayer.startPlaying(binding.root.context,song)
                        it.context.startActivity(Intent(it.context,PlayerActivity::class.java))
                    }
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = SubListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return songIdList.size
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(songIdList[position])
    }
}
