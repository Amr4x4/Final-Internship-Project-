package com.example.speedotransfer.sign_up

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speedotransfer.R
import com.example.speedotransfer.isNetworkAvailable
import com.example.speedotransfer.sign_in.SignIn
import com.example.speedotransfer.sign_up.SignUpSecondScreen
import com.example.speedotransfer.user_connection_issues.InternetConnection
import com.example.speedotransfer.ui.theme.*

class SignUpFirstScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeedoTransferTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SignUpScreen()
                }
            }
        }
    }
}

@Composable
fun SignUpScreen() {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var showInternetConnection by remember { mutableStateOf(false) }

    val passwordValid = remember(password) {
        password.length >= 6 &&
                password.any { it.isUpperCase() } &&
                password.any { it.isLowerCase() } &&
                password.any { it in "!@#$%^&*()-=_+[]{}|;:',.<>?/" }
    }

    val emailValid = remember(email) {
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    val allFieldsValid = remember(fullName, emailValid, passwordValid) {
        fullName.isNotBlank() && emailValid && passwordValid
    }

    val context = LocalContext.current

    // Check for internet connection
    LaunchedEffect(Unit) {
        if (showInternetConnection) {
            showInternetConnection = !isNetworkAvailable(context)
        }
    }

    if (showInternetConnection) {
        InternetConnection(context)
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BackgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(62.dp))

                Text(
                    text = stringResource(id = R.string.sing_up),
                    style = MaterialTheme.typography.h5,
                    color = TextColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    lineHeight = 30.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(82.dp))

                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
                    color = TextColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W600,
                    lineHeight = 30.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(65.dp))

                // Full Name Field
                Text(
                    text = "Full Name",
                    color = TextColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.align(Alignment.Start)
                )
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    placeholder = { Text(text = "Enter your Full Name", color = PlaceholderColor) },
                    trailingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.user),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        textColor = TextColor,
                        placeholderColor = PlaceholderColor
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Email Field
                Text(
                    text = "Email",
                    color = TextColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.align(Alignment.Start)
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = {
                        Text(
                            text = "Enter your email address",
                            color = PlaceholderColor
                        )
                    },
                    trailingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.email),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Email
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        textColor = TextColor,
                        placeholderColor = PlaceholderColor
                    )
                )

                if (email.isNotBlank() && !emailValid) {
                    Text(
                        text = "Wrong email format",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Password Field
                Text(
                    text = "Password",
                    color = TextColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.align(Alignment.Start)
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text(text = "Enter your password", color = PlaceholderColor) },
                    singleLine = true,
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisibility)
                            R.drawable.open_eye
                        else
                            R.drawable.close_eye

                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            Image(
                                painter = painterResource(id = image),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        textColor = TextColor,
                        placeholderColor = PlaceholderColor
                    )
                )

                if (password.isNotBlank() && !passwordValid) {
                    Text(
                        text = stringResource(id = R.string.password_rules),
                        color = DescriptionColor,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (allFieldsValid) {
                            if (isNetworkAvailable(context)) {
                                val intent = Intent(context, SignUpSecondScreen::class.java)
                                context.startActivity(intent)
                            } else {
                                showInternetConnection = true
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (allFieldsValid) BtnRed else LightRed,
                        disabledBackgroundColor = LightRed
                    ),
                    enabled = allFieldsValid
                ) {
                    Text(
                        text = "Sign up",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = {
                    val intent = Intent(context, SignIn::class.java)
                    context.startActivity(intent)
                }) {
                    Text(
                        text = "Already have an account? ",
                        color = Gray,
                        fontSize = 14.sp
                    )
                    Text(
                        text = stringResource(id = R.string.sing_in),
                        color = BtnRed,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable {
                            val intent = Intent(context, SignIn::class.java)
                            context.startActivity(intent)
                        },
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SpeedoTransferTheme {
        SignUpScreen()
    }
}
