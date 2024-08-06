package com.example.speedotransfer.user_connection_issues

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.speedotransfer.R
import com.example.speedotransfer.ui.theme.BtnRed
import com.example.speedotransfer.ui.theme.DarkGray
import com.example.speedotransfer.ui.theme.SpeedoTransferTheme
import com.example.speedotransfer.ui.theme.White

class ServerConnection : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeedoTransferTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ServerError()
                }
            }
        }
    }
}
@Composable
fun ServerError() {
    val context = LocalContext.current

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
                painter = painterResource(id = R.drawable._04_error),
                contentDescription = stringResource(id = R.string.server_error_description),
                modifier = Modifier
                    .height(195.dp)
                    .width(273.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "Server error...",
                fontWeight = FontWeight.W600,
                fontSize = 24.sp,
                lineHeight = 36.sp,
                color = DarkGray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.server_error_description),
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                modifier = Modifier.width(350.dp),
                textAlign = TextAlign.Center,
                color = DarkGray
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:123123")
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(51.dp),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BtnRed,
                    contentColor = Color.White
                )
            ) {
                Text("Call Us")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:bankmasir55@gmail.com")
                        putExtra(Intent.EXTRA_SUBJECT, "Customer Support")
                        putExtra(Intent.EXTRA_TEXT, "Hello, I need help with...")
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(51.dp),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = BtnRed
                ),
                border = BorderStroke(1.dp, BtnRed)
            ) {
                Text("Message Us")
            }
        }
    }
}

@Preview
@Composable
private fun ServerErrorPreview() {
    ServerError()
}