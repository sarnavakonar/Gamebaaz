package database.entity

import main.util.Constants.GAMES_TABLE
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.double
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object GamesTable : Table<GamesEntity>(GAMES_TABLE) {
    val id = int("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val description = varchar("description").bindTo { it.description }
    val category = varchar("category").bindTo { it.categoty }
    val released = varchar("released").bindTo { it.released }
    val image = varchar("image").bindTo { it.image }
    val video = varchar("video").bindTo { it.video }
    val developer = int("developer").bindTo { it.developer }
    val rating = double("rating").bindTo { it.rating }
    val trending = int("trending").bindTo { it.trending }
    val screenshot1 = varchar("screenshot1").bindTo { it.screenshot1 }
    val screenshot2 = varchar("screenshot2").bindTo { it.screenshot2 }
    val screenshot3 = varchar("screenshot3").bindTo { it.screenshot3 }
    val screenshot4 = varchar("screenshot4").bindTo { it.screenshot4 }
}

interface GamesEntity : Entity<GamesEntity> {
    companion object : Entity.Factory<GamesEntity>()
    val id: Int
    var name: String
    var description: String
    var released: String
    var categoty: String
    var image: String
    var video: String
    var developer: Int
    var rating: Double
    var trending: Int
    var screenshot1: String
    var screenshot2: String
    var screenshot3: String
    var screenshot4: String
}