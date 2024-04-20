package com.example.lastmusicapp.models
data class ParentModel(
    val name: String,
    val coverUrl: String,
    var songs: List<String>,
    var isExpanded: Boolean = false,
) {
    constructor() : this("", "", listOf())
}
