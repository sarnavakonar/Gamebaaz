package main.database.entity

import main.util.Constants
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int

object FavouritesTable : Table<FavouritesEntity>(Constants.FAVOURITES_TABLE) {
    val id = int("id").primaryKey().bindTo { it.id }
    val userid = int("userid").bindTo { it.userid }
    val gameid = int("gameid").bindTo { it.gameid }
}

interface FavouritesEntity : Entity<FavouritesEntity> {
    companion object : Entity.Factory<FavouritesEntity>()
    val id: Int
    var userid: Int
    var gameid: Int
}