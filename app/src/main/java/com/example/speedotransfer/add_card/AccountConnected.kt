package com.example.speedotransfer.add_card

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speedotransfer.R
import com.example.speedotransfer.app_core.HomeScreen
import com.example.speedotransfer.ui.theme.BtnRed
import com.example.speedotransfer.ui.theme.SpeedoTransferTheme
import com.example.speedotransfer.ui.theme.White


class AccountConnectedActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeedoTransferTheme {
                AccountConnected()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountConnected(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text(
                        "Add Card",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        val intent = Intent(context, OtpScreenActivity::class.java)
                        context.startActivity(intent)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 100.dp)

            ) {
                Image(
                    painterResource(id = R.drawable.success),
                    contentDescription = "Account Connected Successfully",
                    modifier = Modifier.size(125.dp)
                )
                Spacer(modifier = Modifier.height(56.dp))
                Text(
                    text = "Account Connected Successfully!", style = TextStyle(
                        fontFamily = FontFamily.Default,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W600,
                        lineHeight = 36.sp,
                        textAlign = TextAlign.Center
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Feel free to connect another account at the same time.",
                    style = TextStyle(
                        fontFamily = FontFamily.Default,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        lineHeight = 24.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {

                androidx.compose.material.Button(
                    onClick = {
                        val intent = Intent(context, CurrencySelectionActivity::class.java)
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = BtnRed),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)

                ) {

                    Text(
                        text = "Connect Another Account",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                androidx.compose.material.Button(
                    onClick = {
                        val intent = Intent(context, HomeScreen::class.java)
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .border(1.5.dp, color = BtnRed)

                ) {
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAccountConnected() {

    AccountConnected()

}
