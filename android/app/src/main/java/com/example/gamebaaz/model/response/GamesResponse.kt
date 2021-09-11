package com.example.gamebaaz.model.response

import com.example.gamebaaz.model.Developer
import com.example.gamebaaz.model.Game

data class GamesResponse(
    val categories: List<Category>,
    val developers: List<Developer>
)

data class Category(
    val name: String,
    val games: List<Game>
)
