package com.example.gamebaaz.model

data class Game(
    val id: Int,
    var name: String,
    var description: String? = null,
    var released: String? = null,
    var categoty: String,
    var image: String,
    var video: String? = null,
    var developer: Developer? = null,
    var rating: Double? = null,
    var trending: Int,
    val screenshots: List<String>?,
    var isFavourite: Boolean = false
)
