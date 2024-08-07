package com.example.speedotransfer.sign_in

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.verticalScroll
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
import com.example.speedotransfer.api.LoginRequest
import com.example.speedotransfer.api.LoginResult
import com.example.speedotransfer.api.RetrofitClient
import com.example.speedotransfer.api.UserRepository
import com.example.speedotransfer.api.UserViewModel
import com.example.speedotransfer.app_core.HomeScreen
import com.example.speedotransfer.sign_up.SignUpFirstScreen
import com.example.speedotransfer.ui.theme.*

class SignIn : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = UserRepository(RetrofitClient.apiService)
        val viewModel = UserViewModel(repository)

        setContent {
            SpeedoTransferTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SignInScreen(viewModel = viewModel)
                }
            }
        }
    }
}


@Composable
fun SignInScreen(viewModel: UserViewModel) {
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val emailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val allFieldsValid = email.isNotBlank() && password.isNotBlank() && emailValid

    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginResult.Success -> {
                val intent = Intent(context, HomeScreen::class.java)
                context.startActivity(intent)
            }
            is LoginResult.Error -> {
                errorMessage = (loginState as LoginResult.Error).message
            }
            null -> { /* No action required */ }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(62.dp))

            Text(
                text = stringResource(id = R.string.sing_in),
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

            Spacer(modifier = Modifier.height(56.dp))

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

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (allFieldsValid) {
                        val loginRequest = LoginRequest(email, password)
                        viewModel.login(loginRequest, context)
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
                    text = stringResource(id = R.string.sing_in),
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = {
                val intent = Intent(context, SignUpFirstScreen::class.java)
                context.startActivity(intent)
            }) {
                Text(
                    text = "Don't have an account? ",
                    color = Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 24.sp,
                )
                Text(
                    text = stringResource(id = R.string.sing_up),
                    color = BtnRed,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    lineHeight = 21.sp,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInPreview() {
    val repository = UserRepository(RetrofitClient.apiService)
    val viewModel = UserViewModel(repository)
    SignInScreen(viewModel)
}
