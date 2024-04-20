import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.lastmusicapp.MainActivity
//import com.example.lastmusicapp.SongsListActivity.Companion.category
import com.example.lastmusicapp.adapters.SubListAdapter
import com.example.lastmusicapp.databinding.ParentItemBinding
import com.example.lastmusicapp.models.ParentModel

class MainListAdapter(private val categoryList: List<ParentModel>,private val context: Context) :
    RecyclerView.Adapter<MainListAdapter.MyViewHolder>() {
//    lateinit var context: Context

    class MyViewHolder(val binding: ParentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
//        fun bindData(category: ParentModel, context: Context) {
//            binding.nameTextView.text = category.name
//
//            Glide.with(binding.coverImageView).load(category.coverUrl)
//                .apply(RequestOptions().transform(RoundedCorners(32)))
//                .into(binding.coverImageView)
//
//            binding.root.setOnClickListener {
////                SongsListActivity.category = category
//                binding.songsListRecyclerView.visibility = if (binding.songsListRecyclerView.isShown) View.GONE else View.VISIBLE
////                binding.root.context.startActivity(Intent(binding.root.context, MainActivity::class.java))
//            }
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val viw=LayoutInflater.from(parent.context).inflate(R.layout.parent_item,parent,false)
//        return MyViewHolder(viw)
        val binding = ParentItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            val collection = categoryList[position]
            // Set the nameTextView text
            nameTextView.text = collection.name

            // Load the image using Glide
            Glide.with(coverImageView)
                .load(collection.coverUrl) // Access coverUrl from collection
                .apply(RequestOptions().transform(RoundedCorners(32)))
                .into(coverImageView)
            // Set up the SubListAdapter for the songsListRecyclerView
            setupRecyclerView(
                songsListRecyclerView,
                collection.songs
            )
            // Toggle visibility of songsListRecyclerView when nameTextView is clicked
//            nameTextView.setOnClickListener {
//                songsListRecyclerView.visibility =
//                    if (songsListRecyclerView.isShown) View.GONE else View.VISIBLE
//            }
        }
    }

    fun setupRecyclerView(songsListRecyclerView: RecyclerView, songs: List<String>) {
        val adapter = SubListAdapter(songs)
        songsListRecyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        songsListRecyclerView.adapter = adapter
    }


}
