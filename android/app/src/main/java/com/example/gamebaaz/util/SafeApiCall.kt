package com.example.gamebaaz.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

object SafeApiCall {

    suspend inline fun <T> safeApiCall(crossinline responseFunction: suspend () -> T ): Resource<T> {
        return withContext(Dispatchers.IO){
            try {
                Resource.success(responseFunction.invoke())
            }
            catch (e: Exception) {
                e.printStackTrace()
                val error: String? = when(e){
                    is HttpException -> {
                        e.message()
                    }
                    is SocketTimeoutException -> {
                        "Timeout"
                    }
                    is IOException -> {
                        "Network error"
                    }
                    else -> {
                        e.message
                    }
                }
                Resource.error("$error")
            }
        }
    }

}