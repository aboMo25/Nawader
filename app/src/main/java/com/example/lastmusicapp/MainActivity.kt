package com.example.lastmusicapp


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.lastmusicapp.adapters.CategoryAdapter
import com.example.lastmusicapp.adapters.SectionSongListAdapter
import com.example.lastmusicapp.databinding.ActivityMainBinding
import com.example.lastmusicapp.models.CategoryModel
import com.example.lastmusicapp.models.SongModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObjects


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var categoryAdapter: CategoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getCategories()
        binding.menue.setOnClickListener {

        }


////////////////////////////////////////////////////////////////
//        setupSection("section_1",binding.section1MainLayout,binding.section1Title,binding.section1RecyclerView)
//        setupSection("section_2",binding.section2MainLayout,binding.section2Title,binding.section2RecyclerView)
//        setupSection("section_3",binding.section3MainLayout,binding.section3Title,binding.section3RecyclerView)
//        setupSection("section_4",binding.section4MainLayout,binding.section4Title,binding.section4RecyclerView)
//        setupSection("section_5",binding.section5MainLayout,binding.section5Title,binding.section5RecyclerView)
//        setupMostlyPlayed("mostly_played",binding.mostlyPlayedMainLayout,binding.mostlyPlayedTitle,binding.mostlyPlayedRecyclerView)
        //////////////
///////////        setupSearch()
//////////////////////////////////////////////////////////////////
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
                val categoryList=it.toObjects(CategoryModel::class.java)
                setupCategoryRecyclerView(categoryList)
            }
    }
    fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation item clicks here
        when (item.itemId) {
            // Handle different menu items
        }
        return true
    }


    fun setupCategoryRecyclerView(categoryList : List<CategoryModel>){
        categoryAdapter = CategoryAdapter(categoryList)
        binding.categoriesItemRecyclerView1.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.categoriesItemRecyclerView1.adapter = categoryAdapter
    }

    // Sections
    fun setupSection(id : String, mainLayout : RelativeLayout, titleView : TextView, recyclerView: RecyclerView){
        FirebaseFirestore.getInstance().collection("sections")
            .document(id)
            .get().addOnSuccessListener {
                val section = it.toObject(CategoryModel::class.java)
                section?.apply {
                    mainLayout.visibility = View.VISIBLE
                    titleView.text = name
                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
                    recyclerView.adapter = SectionSongListAdapter(songs)
                    mainLayout.setOnClickListener {
                        SongListActivity.category = section
                        startActivity(Intent(this@MainActivity,SongListActivity::class.java))
                    }
                }
            }

    }


    fun setupMostlyPlayed(id : String,mainLayout : RelativeLayout,titleView : TextView,recyclerView: RecyclerView){
        FirebaseFirestore.getInstance().collection("sections")
            .document(id)
            .get().addOnSuccessListener {
                //get most played songs
                FirebaseFirestore.getInstance().collection("songs")
                    .orderBy("count", Query.Direction.DESCENDING)
                    .limit(5)
                    .get().addOnSuccessListener {songListSnapshot->
                        val songsModelList = songListSnapshot.toObjects<SongModel>()
                        val songsIdList = songsModelList.map{
                            it.id
                        }.toList()
                        val section = it.toObject(CategoryModel::class.java)
                        section?.apply {
                            section.songs=songsIdList
                            mainLayout.visibility = View.VISIBLE
                            titleView.text = name
                            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
                            recyclerView.adapter = SectionSongListAdapter(songs)
                            mainLayout.setOnClickListener {
                                SongListActivity.category = section
                                startActivity(Intent(this@MainActivity,SongListActivity::class.java))
                            }
                        }
                    }
            }
    }

    fun search(query: String, recyclerView: RecyclerView) {
        FirebaseFirestore.getInstance().collection("category")
            .whereEqualTo("name", query) // Change "title" to the field you want to search against
            .get()
            .addOnSuccessListener { songListSnapshot ->
                if (songListSnapshot != null) {
                    val songsModelList = songListSnapshot.toObjects<SongModel>()
                    val songTitles = songsModelList.map { it.title } // Extract titles from SongModel objects
                    recyclerView.adapter = SectionSongListAdapter(songTitles)
                } else {
                    // Handle null snapshot
                    Log.e("Search", "Snapshot is null")
                }
            }
            .addOnFailureListener { exception ->
                // Handle failures
                Log.e("Search", "Error searching songs: $exception")
            }
    }


////////////////////////////////////////////////////////
    // Setup search functionality
//    fun setupSearch() {
//        binding.searchButton.setOnClickListener {
//            val query = binding.searchEditText.text.toString().trim()
//            if (query.isNotEmpty()) {
//                search(query, binding.searchResultsRecyclerView)
//            } else {
//                // Handle empty search query
//                // You can display a message to the user or handle it in any other way
//            }
//        }
//    }

///////////////////////////////////////////////////////


}