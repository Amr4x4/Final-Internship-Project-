package com.example.speedotransfer.sign_up

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speedotransfer.R
import com.example.speedotransfer.api.RegisterRequest
import com.example.speedotransfer.api.RetrofitClient
import com.example.speedotransfer.api.UserRepository
import com.example.speedotransfer.isNetworkAvailable
import com.example.speedotransfer.sign_in.SignIn
import com.example.speedotransfer.user_connection_issues.InternetConnection
import com.example.speedotransfer.ui.theme.SpeedoTransferTheme
import kotlinx.coroutines.launch

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
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var nationality by remember { mutableStateOf("") }
    var nationalIdNumber by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("MALE") }
    var dateOfBirth by remember { mutableStateOf("1990-01-01") }
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

    val allFieldsValid = remember(firstName, lastName, emailValid, passwordValid) {
        firstName.isNotBlank() && lastName.isNotBlank() && emailValid && passwordValid
    }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Check for internet connection
    LaunchedEffect(Unit) {
        showInternetConnection = !isNetworkAvailable(context)
    }

    if (showInternetConnection) {
        InternetConnection(context)
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
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
                    color = Color.Black,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(82.dp))

                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(65.dp))

                // First Name Field
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text(text = "First Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Last Name Field
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text(text = "Last Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Email Field
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = "Email") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Email
                    ),
                    modifier = Modifier.fillMaxWidth()
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
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = "Password") },
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
                            Icon(
                                painter = painterResource(id = image),
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                if (password.isNotBlank() && !passwordValid) {
                    Text(
                        text = stringResource(id = R.string.password_rules),
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (allFieldsValid) {
                            if (isNetworkAvailable(context)) {
                                coroutineScope.launch {
                                    try {
                                        val registerRequest = RegisterRequest(
                                            firstName = firstName,
                                            lastName = lastName,
                                            email = email,
                                            phoneNumber = phoneNumber,
                                            address = address,
                                            nationality = nationality,
                                            nationalIdNumber = nationalIdNumber,
                                            gender = gender,
                                            dateOfBirth = dateOfBirth,
                                            password = password
                                        )
                                        val userRepository = UserRepository(RetrofitClient.apiService)
                                        val response = userRepository.registerUser(registerRequest)

                                        if (response.message == "User Registered Successfully") {
                                            Toast.makeText(context, "User Registered Successfully", Toast.LENGTH_SHORT).show()
                                            context.startActivity(Intent(context, SignIn::class.java))
                                        } else {
                                            Toast.makeText(context, "Registration Failed: ${response.message}", Toast.LENGTH_SHORT).show()
                                        }
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Registration Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                showInternetConnection = true
                            }
                        } else {
                            Toast.makeText(context, "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    enabled = allFieldsValid
                ) {
                    Text(text = stringResource(id = R.string.sing_up))
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Sign In link
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Already have an account?")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Sign In",
                        color = Color.Red,
                        modifier = Modifier.clickable {
                            context.startActivity(Intent(context, SignIn::class.java))
                        }
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
