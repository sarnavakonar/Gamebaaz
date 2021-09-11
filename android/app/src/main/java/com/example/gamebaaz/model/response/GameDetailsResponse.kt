package com.example.gamebaaz.model.response

import com.example.gamebaaz.model.Game

data class GameDetailsResponse(
    val status: String,
    val message: String,
    val gamesData: Game?
)
