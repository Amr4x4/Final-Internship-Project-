package com.example.speedotransfer.user_connection_issues

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speedotransfer.R
import com.example.speedotransfer.isNetworkAvailable
import com.example.speedotransfer.sign_in.SignIn
import com.example.speedotransfer.ui.theme.BtnRed
import com.example.speedotransfer.ui.theme.DarkGray
import com.example.speedotransfer.ui.theme.SpeedoTransferTheme
import com.example.speedotransfer.ui.theme.White

class InternetConnectionIssues : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeedoTransferTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val context = LocalContext.current
                    InternetConnection(context)
                }
            }
        }
    }
}
@Composable
fun InternetConnection(context: Context) {
    var internetAvailable by remember { mutableStateOf(true) }

    fun checkInternetConnection() {
        internetAvailable = isNetworkAvailable(context)
        if (internetAvailable) {
            val intent = Intent(context, SignIn::class.java)
            context.startActivity(intent)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.no_internet),
                contentDescription = stringResource(id = R.string.internet_disable),
                modifier = Modifier
                    .height(109.dp)
                    .width(109.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = stringResource(id = R.string.internet_disable),
                Modifier.width(300.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W600,
                fontSize = 24.sp,
                lineHeight = 36.sp,
                color = DarkGray
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { checkInternetConnection() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(51.dp),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BtnRed,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Update",
                    Modifier.width(300.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W500,
                    fontSize = 16.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun InternetConnectionPreview() {
    InternetConnection(LocalContext.current)
}
