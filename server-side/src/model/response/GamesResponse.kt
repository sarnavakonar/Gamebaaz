package main.model.response

import main.model.Developer
import main.model.Game

data class GamesResponse(
    val categories: List<Category>,
    val developers: List<Developer>
)

data class Category(
    val name: String,
    val games: List<Game>
)
