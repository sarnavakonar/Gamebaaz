package com.example.gamebaaz.model.response

import com.example.gamebaaz.model.Game

data class GamesListResponse(
    val status: String,
    val message: String,
    val games: List<Game>
)
