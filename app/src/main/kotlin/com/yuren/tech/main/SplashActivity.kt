package com.yuren.tech.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.window.SplashScreenView
import androidx.activity.ComponentActivity
import com.yuren.tech.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle();
        setContentView(R.layout.splash_screen)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { view: SplashScreenView? -> }
        }

        Log.e("xxx","   SplashActivity    ");


        GlobalScope.launch {
            delay(2000)
            goMain();
         }


    }

    private fun setStyle(){
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    private fun goMain(){
       val intent = Intent(this, MainActivity::class.java)
       startActivity(intent);
       Log.e("xxx","   goMain  goMain    ");

      // finish();
    }
}