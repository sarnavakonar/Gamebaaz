package main.model.response

import main.model.Game

data class GameDetailResponse(
    val status: String,
    val message: String,
    val gamesData: Game?
)
