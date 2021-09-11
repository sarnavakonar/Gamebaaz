package com.example.gamebaaz.data.remote

import com.example.gamebaaz.model.request.AlterFavouriteRequestBody
import com.example.gamebaaz.model.request.CreateUserRequest
import com.example.gamebaaz.model.response.GameDetailsResponse
import com.example.gamebaaz.model.response.GamesResponse
import com.example.gamebaaz.model.response.GamesListResponse
import retrofit2.http.*

interface ApiInterface {

    @POST("/createUser")
    suspend fun createUser(
        @Body request: CreateUserRequest,
    ) : GameDetailsResponse

    @GET("/getAllGames")
    suspend fun getAllGames(
        @Header("Authorization") header: String
    ) : GamesResponse

    @GET("/searchGame")
    suspend fun searchGames(
        @Header("Authorization") header: String,
        @Query("param") param: String
    ) : GamesListResponse

    @GET("/getGamesBy")
    suspend fun getGamesByDev(
        @Header("Authorization") header: String,
        @Query("devId") devId: Int
    ) : GamesListResponse

    @GET("/getAllFavourites")
    suspend fun getFavGames(
        @Header("Authorization") header: String
    ) : GamesListResponse

    @GET("/getGameDetail")
    suspend fun getGameDetails(
        @Header("Authorization") header: String,
        @Query("gameId") gameId: Int
    ) : GameDetailsResponse

    @POST("/alterFav")
    suspend fun alterFav(
        @Header("Authorization") header: String,
        @Body request: AlterFavouriteRequestBody,
    ) : GameDetailsResponse

}