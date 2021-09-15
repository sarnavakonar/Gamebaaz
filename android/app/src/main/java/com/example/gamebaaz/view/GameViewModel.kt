package com.example.gamebaaz.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamebaaz.model.Developer
import com.example.gamebaaz.model.request.AlterFavouriteRequestBody
import com.example.gamebaaz.model.request.CreateUserRequest
import com.example.gamebaaz.model.response.GameDetailsResponse
import com.example.gamebaaz.model.response.GamesResponse
import com.example.gamebaaz.model.response.GamesListResponse
import com.example.gamebaaz.repository.Repository
import com.example.gamebaaz.util.Resource
import com.example.gamebaaz.util.Status
import com.example.gamebaaz.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor (private val repository: Repository): ViewModel() {

    private lateinit var token: String

    private val loginResponse: MutableLiveData<Resource<GameDetailsResponse>> = MutableLiveData()

    private val gameData: MutableLiveData<Resource<GamesResponse>> = MutableLiveData()

    private val gamesByDev: MutableLiveData<HashMap<Int, Resource<GamesListResponse>>> = MutableLiveData()
    private var developer: Developer? = null

    private var searchGameJob : Job? = null
    private val searchedGames: MutableLiveData<Resource<GamesListResponse>> = MutableLiveData()
    private val searchedText: MutableLiveData<String> = MutableLiveData()

    private val favGames: MutableLiveData<Resource<GamesListResponse>> = MutableLiveData()

    private val gameDetails : MutableLiveData<HashMap<Int,Resource<GameDetailsResponse>>> = MutableLiveData()
    private var gameId: Int = -1

    private var alterFavGameJob : Job? = null
    private val isFavGame: MutableLiveData<Resource<GameDetailsResponse>> = MutableLiveData()

    fun getLoginResponse(): LiveData<Resource<GameDetailsResponse>> = loginResponse

    fun getGames(): LiveData<Resource<GamesResponse>> = gameData

    fun getGamesByDev(): MutableLiveData<HashMap<Int, Resource<GamesListResponse>>> = gamesByDev

    fun getSearchedGames(): LiveData<Resource<GamesListResponse>> = searchedGames

    fun setDeveloper(dev: Developer){
        developer = dev
    }

    fun getDeveloper(): Developer? = developer

    fun getSearchedText(): LiveData<String> = searchedText

    fun setSearchedText(text: String) {
        searchedText.value = text
    }

    fun getFavGames(): LiveData<Resource<GamesListResponse>> = favGames

    fun getGameDetails(): LiveData<HashMap<Int,Resource<GameDetailsResponse>>> = gameDetails

    fun getGamedId(): Int = gameId

    fun setGameId(id: Int) {
        gameId = id
    }

    fun isFavGame(): LiveData<Resource<GameDetailsResponse>> = isFavGame

    fun getLoginCredentials(): String? {
        return repository.getCredentials()
    }

    fun getUsername(): String? {
        return repository.getUsername()
    }

    fun setToken() {
        token = "Basic ${Utils.getBase64(getLoginCredentials()!!)}"
    }

    fun createUser(username: String, password: String) {
        val request = CreateUserRequest(
            username = username,
            password = password
        )
        loginResponse.value = Resource.loading()
        viewModelScope.launch {
            loginResponse.value = repository.createUser(request = request)
            if (loginResponse.value!!.status == Status.SUCCESS) {
                repository.saveCredentials(
                    username = username,
                    password = password
                )
            }
        }
    }

    fun getAllGames() {
        setToken()

        if(gameData.value?.status == Status.SUCCESS)
            return

        gameData.value = Resource.loading()
        viewModelScope.launch {
            gameData.value = repository.getAllGames(token)
        }
    }

    fun searchGames(){
        cancelSearch()
        searchedGames.value = Resource.loading()
        searchGameJob = viewModelScope.launch {
            searchedGames.value = repository
                .searchGames(
                    header = token,
                    param = searchedText.value!!
                )
        }
    }

    fun cancelSearch(){
        searchGameJob?.cancel()
    }

    fun fetchGamesByDev(){
        val map = if(gamesByDev.value == null){
            HashMap()
        }
        else{
            gamesByDev.value
        }

        developer?.let { developer ->

            if(map?.get(developer.id) != null){
                Log.e("lala", "${developer.name} already present")
                gamesByDev.value = map
            }
            else {
                Log.e("lala", "${developer.name} absent")
                map?.put(developer.id, Resource.loading())
                gamesByDev.value = map

                viewModelScope.launch {
                    val games = repository
                        .getGamesByDev(
                            header = token,
                            devId = developer.id
                        )
                    map?.put(developer.id, games)
                    gamesByDev.value = map
                }
            }
        }
    }

    fun fetchFavGames(){
        if(favGames.value?.status != Status.SUCCESS)
            favGames.value = Resource.loading()

        viewModelScope.launch {
            favGames.value = repository
                .getFavGames(
                    header = token
                )
        }
    }

    fun fetchGameDetails(){
        val map = if(gameDetails.value == null){
            HashMap()
        }
        else{
            gameDetails.value
        }

        if(map?.get(gameId) != null){
            Log.e("lala", "${map.get(gameId)?.data?.gamesData?.name} already present")
            gameDetails.value = map
        }
        else{
            Log.e("lala", "game data (id is $gameId) absent")
            map?.set(gameId, Resource.loading())
            gameDetails.value = map

            viewModelScope.launch {
                val gameData = repository
                    .getGameDetails(
                        header = token,
                        gameId = gameId
                    )
                map?.set(gameId, gameData)
                gameDetails.value = map
            }
        }
    }

    fun alterFavouriteGame(addAsFav: Boolean) {
        cancelAlterFavGame()
        alterFavGameJob = viewModelScope.launch {
            isFavGame.value = repository
                .alterFav(
                    header = token,
                    request = AlterFavouriteRequestBody(
                        gameId = gameId,
                        addToFav = addAsFav
                    )
                )
            if(isFavGame.value?.status == Status.SUCCESS){
                val game = gameDetails.value?.get(gameId)
                game?.data?.gamesData?.isFavourite = addAsFav
            }
        }
    }

    private fun cancelAlterFavGame(){
        alterFavGameJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        //viewModelScope.cancel()
    }
}