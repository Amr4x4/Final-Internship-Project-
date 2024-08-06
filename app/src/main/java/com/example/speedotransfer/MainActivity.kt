package com.example.speedotransfer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.speedotransfer.onboarding.OnboardingPager
import com.example.speedotransfer.sign_up.SignUpFirstScreen
import com.example.speedotransfer.ui.theme.SpeedoTransferTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)

        setContent {
            SpeedoTransferTheme {
                if (isFirstLaunch) {
                    OnboardingPager()
                } else {
                    // Navigate to your main app screen
                    // MainAppScreen()
                    val intent = Intent(this, SignUpFirstScreen::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
