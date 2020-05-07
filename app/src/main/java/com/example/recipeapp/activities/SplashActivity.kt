package com.example.recipeapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.recipeapp.R

class SplashActivity : AppCompatActivity() {

    // 2.5 seconds.
    private val splashDelay: Long = 2500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        goToHomeScreen()
    }

    private fun goToHomeScreen(){
        // Use Handler to wait 1 second before opening the HomeActivity.
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
            // Call finish, so user can not return to splash screen.
            finish()

            // Animation to fade in and fade out the SplashActivity.
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }, splashDelay)
    }
}

