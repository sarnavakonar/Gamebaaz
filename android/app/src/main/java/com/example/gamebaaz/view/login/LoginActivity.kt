package com.example.gamebaaz.view.login

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.gamebaaz.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        //setup()
    }

    private fun setup(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
    }

}