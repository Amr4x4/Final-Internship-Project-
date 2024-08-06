package com.example.speedotransfer.add_card

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.speedotransfer.app_core.MoreScreen
import com.example.speedotransfer.ui.theme.GrayG100
import com.example.speedotransfer.ui.theme.PrimaryP300
import com.example.speedotransfer.ui.theme.SpeedoTransferTheme


class OtpScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeedoTransferTheme {
                OtpScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpScreen(email: String = "Email@gmail.com") {
    var otpText by remember { mutableStateOf("") }
    val isOtpComplete = otpText.length == 6
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
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = buildAnnotatedString {
                    append("Enter the digits verification code send to ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(email)
                    }
                }, modifier = Modifier.padding(20.dp), textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            OtpTextField(
                otpText = otpText,
                onOtpTextChange = { text, isComplete ->
                    otpText = text
                }
            )
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = buildAnnotatedString {
                    append("Don’t receive OTP?  ")
                    withStyle(
                        style = SpanStyle(
                            color = PrimaryP300,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("Resend OTP")
                    }
                }, fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(120.dp))
            Button(
                onClick = {
                    val intent = Intent(context, AccountConnectedActivity::class.java)
                    context.startActivity(intent) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(6.dp),
                enabled = isOtpComplete,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isOtpComplete) PrimaryP300 else Color.Yellow,
                    contentColor = Color.White
                )
            ) {
                Text(text = if (isOtpComplete) "Sign On" else "Submit Refill")
            }
        }
    }
}

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    onOtpTextChange: (String, Boolean) -> Unit
) {
    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= 6) {
                onOtpTextChange.invoke(it.text, it.text.length == 6)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(
            ) {
                repeat(6) { index ->
                    CharView(
                        index = index,
                        text = otpText
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String
) {
    val isFocused = text.length == index
    val char = when {
        index >= text.length -> ""
        else -> text[index].toString()
    }
    Box(
        modifier = Modifier
            .width(40.dp)
            .height(55.dp)
            .border(
                1.dp, when {
                    isFocused -> PrimaryP300
                    index < text.length -> PrimaryP300
                    else -> GrayG100
                }, RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = char,
            color = if (isFocused) {
                GrayG100
            } else {
                PrimaryP300
            },
            textAlign = TextAlign.Center,
            fontSize = 32.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOtpScreen() {
    OtpScreen()
}
