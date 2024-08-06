package com.example.speedotransfer.add_card

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speedotransfer.ui.theme.SpeedoTransferTheme
import com.example.speedotransfer.user_connection_issues.InternetConnectionIssues
import kotlinx.coroutines.delay

class ConnectingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeedoTransferTheme {
                Connecting()
            }
        }
    }
}

@Composable
fun Connecting(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        delay(2000)
        val intent = if (isInternetAvailable(context)) {
            Intent(context, OtpScreenActivity::class.java)
        } else {
            Intent(context, InternetConnectionIssues::class.java)
        }
        context.startActivity(intent)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier.size(150.dp)
            )
            Text(
                text = "Speedo\nTransfer",
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.W600,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "Connecting to Speedo Transfer Credit card",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Center
            ),
        )
    }
}

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

@Preview(showBackground = true)
@Composable
private fun PreviewConnecting() {
    SpeedoTransferTheme {
        Connecting()
    }
}
