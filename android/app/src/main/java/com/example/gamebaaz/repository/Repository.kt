package com.example.gamebaaz.repository

import com.example.gamebaaz.data.local.SharedPrefHelper
import com.example.gamebaaz.data.remote.ApiInterface
import com.example.gamebaaz.model.request.AlterFavouriteRequestBody
import com.example.gamebaaz.model.request.CreateUserRequest
import com.example.gamebaaz.util.SafeApiCall.safeApiCall
import javax.inject.Inject

class Repository @Inject constructor (
    private val apiInterface: ApiInterface,
    private val sharedPrefHelper: SharedPrefHelper
) {

    fun getCredentials() = sharedPrefHelper.getCredential()

    fun getUsername() = sharedPrefHelper.getUsername()

    fun saveCredentials(
        username: String,
        password: String
    ) {
        sharedPrefHelper.saveCredential(username = username, password = password)
    }

    suspend fun createUser(
        request: CreateUserRequest
    ) = safeApiCall {
        apiInterface.createUser(request = request)
    }

    suspend fun getAllGames(
        header: String
    ) = safeApiCall {
         apiInterface.getAllGames(header = header)
    }

    suspend fun searchGames(
        header: String,
        param: String
    ) = safeApiCall {
        apiInterface.searchGames(header = header, param = param)
    }

    suspend fun getGamesByDev(
        header: String,
        devId: Int
    ) = safeApiCall {
        apiInterface.getGamesByDev(header = header, devId = devId)
    }

    suspend fun getFavGames(
        header: String
    ) = safeApiCall {
        apiInterface.getFavGames(header = header)
    }

    suspend fun getGameDetails(
        header: String,
        gameId: Int,
    ) = safeApiCall {
        apiInterface.getGameDetails(header = header, gameId = gameId)
    }

    suspend fun alterFav(
        header: String,
        request: AlterFavouriteRequestBody,
    ) = safeApiCall {
        apiInterface.alterFav(header = header, request = request)
    }

}