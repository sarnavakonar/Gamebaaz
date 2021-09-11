package main.model

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
    var screenshots: List<String?>? = null,
    var isFavourite: Boolean = false
)
