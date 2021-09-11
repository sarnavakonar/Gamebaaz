package database

import database.entity.GamesEntity
import database.entity.GamesTable
import main.database.entity.DevelopersEntity
import main.database.entity.DevelopersTable
import main.database.entity.FavouritesTable
import main.database.entity.UsersTable
import main.model.Developer
import main.model.Game
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.filter
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class DatabaseManager {

    // config
    private val databaseName = "games_server"
    private val username = "root"
    private val password = "bumbabumba1"

    // database
    private val ktormDatabase: Database

    init {
        //val jdbcUrl = "jdbc:mysql://localhost:3306/$databaseName?user=$username&password=$password&useSSL=false"
        val jdbcUrl = "jdbc:mysql://us-cdbr-east-04.cleardb.com:3306/heroku_70ea6ff4c3e3313?user=b3f8bb58a60d20&password=e83a13d6&useSSL=false"
        ktormDatabase = Database.connect(jdbcUrl)
    }

    fun getGames(): List<GamesEntity> {
        return ktormDatabase
            .sequenceOf(GamesTable)
            .toList()
    }

    fun getDevelopers(): List<DevelopersEntity> {
        return ktormDatabase
            .sequenceOf(DevelopersTable)
            .toList()
    }

    fun checkIfUserNameExists(username: String): Int {
        return ktormDatabase
            .from(UsersTable)
            .select(UsersTable.id)
            .where { UsersTable.username eq username }
            .totalRecords
    }

    fun checkIfUserExists(username: String, password: String): Int {
        return ktormDatabase
            .from(UsersTable)
            .select(UsersTable.id)
            .where { (UsersTable.username eq username) and (UsersTable.password eq password) }
            .totalRecords
    }

    fun getAllFavouriteGames(userId: Int): List<Game> {
        val gamesList = mutableListOf<Game>()
        ktormDatabase
            .from(FavouritesTable)
            .innerJoin(GamesTable, on = GamesTable.id eq FavouritesTable.gameid)
            //.innerJoin(DevelopersTable, on = DevelopersTable.id eq GamesTable.developer)
            .select(GamesTable.id,
                GamesTable.name,
                GamesTable.category,
                //GamesTable.description,
                GamesTable.image,
                //GamesTable.video,
                //GamesTable.rating,
                GamesTable.trending
//                DevelopersTable.id,
//                DevelopersTable.name,
//                DevelopersTable.logo,
//                DevelopersTable.about,
//                DevelopersTable.founded,
//                DevelopersTable.twitter,
//                DevelopersTable.insta,
//                DevelopersTable.fb
            )
            .where { FavouritesTable.userid eq userId }
            .forEach {
                gamesList.add(
                    Game(
                        id = it[GamesTable.id]!!,
                        name = it[GamesTable.name]!!,
                        categoty = it[GamesTable.category]!!,
                        image = it[GamesTable.image]!!,
                        trending = it[GamesTable.trending]!!
                    )
                )
            }
        return gamesList
    }

    fun getUserIdFromUsername(username: String): Int? {
        ktormDatabase
            .from(UsersTable)
            .select(UsersTable.id)
            .where { UsersTable.username eq username }
            .forEach {
                return it[UsersTable.id]
            }
        return null
    }

    fun checkIfAlreadyFavourite(userId: Int, gameId: Int): Int {
        return ktormDatabase
            .from(FavouritesTable)
            .select(FavouritesTable.id)
            .where {
                (FavouritesTable.userid eq userId) and (FavouritesTable.gameid eq gameId)
            }
            .totalRecords
    }

    fun addGameAsFavourite(userId: Int, gameId: Int): Int {
        return ktormDatabase.insert(FavouritesTable) {
            set(it.userid, userId)
            set(it.gameid, gameId)
        }
    }

    fun deleteGameAsFavourite(userId: Int, gameId: Int): Int {
        return ktormDatabase.delete(FavouritesTable){
            (it.userid eq userId) and (it.gameid eq gameId)
        }
    }

    fun addNewUser(username: String, password: String): Int {
        return ktormDatabase.insert(UsersTable) {
            set(it.username, username)
            set(it.password, password)
        }
    }

    fun searchGame(param: String): List<Game> {
        val gamesList = mutableListOf<Game>()
        ktormDatabase
            .from(GamesTable)
            .select()
            .where { (GamesTable.name like "%$param%" ) or (GamesTable.description like "%$param%") }
            .forEach {
                gamesList.add(
                    Game(
                        id = it[GamesTable.id]!!,
                        name = it[GamesTable.name]!!,
                        categoty = it[GamesTable.category]!!,
                        description = it[GamesTable.description]!!,
                        image = it[GamesTable.image]!!,
                        trending = it[GamesTable.trending]!!
                    )
                )
            }
        return gamesList
    }

    fun getGamesByDev(param: Int): List<GamesEntity> {
        return ktormDatabase
            .sequenceOf(GamesTable)
            .filter { it.developer eq param }
            .toList()
    }

    fun getGameDetail(gameId: Int): Game? {
        var game: Game? = null
        ktormDatabase
            .from(GamesTable)
            .innerJoin(DevelopersTable, on = GamesTable.developer eq DevelopersTable.id)
            .select(GamesTable.id,
                GamesTable.name,
                GamesTable.category,
                GamesTable.description,
                GamesTable.released,
                GamesTable.image,
                GamesTable.video,
                GamesTable.rating,
                GamesTable.trending,
                GamesTable.screenshot1,
                GamesTable.screenshot2,
                GamesTable.screenshot3,
                GamesTable.screenshot4,
                DevelopersTable.id,
                DevelopersTable.name,
                DevelopersTable.logo,
                DevelopersTable.about,
                DevelopersTable.founded,
                DevelopersTable.twitter,
                DevelopersTable.insta,
                DevelopersTable.fb
            )
            .where { GamesTable.id eq gameId }
            .forEach {
                game = Game(
                    id = it[GamesTable.id]!!,
                    name = it[GamesTable.name]!!,
                    description = it[GamesTable.description],
                    released = it[GamesTable.released],
                    categoty = it[GamesTable.category]!!,
                    image = it[GamesTable.image]!!,
                    video = it[GamesTable.video],
                    developer = Developer(
                        id = it[DevelopersTable.id]!!,
                        name = it[DevelopersTable.name]!!,
                        logo = it[DevelopersTable.logo]!!,
                        about = it[DevelopersTable.about]!!,
                        founded = it[DevelopersTable.founded]!!,
                        twitter = it[DevelopersTable.twitter]!!,
                        insta = it[DevelopersTable.insta]!!,
                        fb = it[DevelopersTable.fb]!!
                    ),
                    rating = it[GamesTable.rating],
                    trending = it[GamesTable.trending]!!,
                    screenshots = listOf(
                        it[GamesTable.screenshot1],
                        it[GamesTable.screenshot2],
                        it[GamesTable.screenshot3],
                        it[GamesTable.screenshot4]
                    )
                )
            }
        return game
    }

}