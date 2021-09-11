package main.model.request

data class FavouriteRequestBody(
    var gameId: Int = -1,
    var addToFav: Boolean = true
)
