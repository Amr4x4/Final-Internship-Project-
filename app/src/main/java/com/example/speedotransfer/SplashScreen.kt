package com.example.speedotransfer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.speedotransfer.ui.theme.DarkRed
import com.example.speedotransfer.ui.theme.SpeedoTransferTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeedoTransferTheme {
                SplashScreenActivity()
            }
        }
    }
}

@Composable
fun SplashScreenActivity(modifier: Modifier = Modifier) {
    var visibleText by remember { mutableStateOf("") }
    val fullText = stringResource(id = R.string.app_name)
    val charDelay = 150L // Delay in milliseconds between each character
    val context = LocalContext.current

    LaunchedEffect(fullText) {
        for (i in fullText.indices) {
            delay(charDelay)
            visibleText = fullText.substring(0, i + 1)
        }
        delay(1000L)
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
        if (context is ComponentActivity) {
            context.finish()
        }

    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DarkRed),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = visibleText,
            fontSize = 32.sp,
            fontWeight = FontWeight.W400,
            color = Color.White
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreenActivity()
}