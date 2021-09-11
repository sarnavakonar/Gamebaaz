package main.model.response

import main.model.Game

data class FavouriteGamesResponse(
    val status: String,
    val message: String,
    val games: List<Game>
)
