package com.example.gamebaaz.model.request

data class AlterFavouriteRequestBody(
    var gameId: Int,
    var addToFav: Boolean = true
)
