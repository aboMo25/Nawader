package com.example.lastmusicapp.models
data class SongModel(
    val id : String,
    val title : String,
    val subtitle : String,
    val url : String,
    val coverUrl : String,
) {

    object Constants {
        const val PARENT = 1
        const val CHILD = 0
    }
    constructor() : this("","","","","")
}