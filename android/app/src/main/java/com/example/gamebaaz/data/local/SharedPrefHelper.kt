package com.example.gamebaaz.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPrefHelper @Inject constructor (@ApplicationContext val context: Context) {

    companion object {
        private const val USERNAME = "username"
        private const val PASSWORD = "password"
    }

    fun saveCredential(username: String, password: String) {
        val sharedPreference =  getPreference()
        val editor = sharedPreference.edit()
        editor.putString(USERNAME, username)
        editor.putString(PASSWORD, password)
        editor.apply()
    }

    fun getCredential(): String? {
        val sharedPreference =  getPreference()
        val username = sharedPreference.getString(USERNAME, null)
        val password = sharedPreference.getString(PASSWORD, null)
        return if (username.isNullOrEmpty() || password.isNullOrEmpty())
            null
        else
            "$username:$password"
    }

    fun getUsername(): String? {
        val sharedPreference =  getPreference()
        return sharedPreference.getString(USERNAME, null)
    }

    private fun getPreference(): SharedPreferences {
        return context.getSharedPreferences("USER_CRED",Context.MODE_PRIVATE)
    }

}