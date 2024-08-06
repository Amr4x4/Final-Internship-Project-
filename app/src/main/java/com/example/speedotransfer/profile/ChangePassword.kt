package com.example.speedotransfer.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speedotransfer.R
import com.example.speedotransfer.ui.theme.BtnRed
import com.example.speedotransfer.ui.theme.LayerBackground1
import com.example.speedotransfer.ui.theme.SpeedoTransferTheme


class ChangePasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeedoTransferTheme {
                ChangePassword()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePassword(modifier: Modifier = Modifier) {
    var context = LocalContext.current
    var oldPasswordVisibility by remember { mutableStateOf(false) }
    var newPasswordVisibility by remember { mutableStateOf(false) }
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text(
                        "Change Password",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        val intent = Intent(context, SettingActivity::class.java)
                        context.startActivity(intent)
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.drop_down),
                            contentDescription = "Back button"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .background(LayerBackground1)
        ) {
            Column(modifier = modifier.padding(16.dp)) {
                Text(text = "Current Password", fontSize = 16.sp)
                OutlinedTextField(
                    value = oldPassword,
                    onValueChange = {
                        oldPassword = it
                    },
                    label = { Text("Enter your password") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = Color.Black,
                        focusedTextColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    trailingIcon = {
                        IconButton(onClick = {
                            oldPasswordVisibility = !oldPasswordVisibility
                        }) {
                            Icon(
                                painter = if (oldPasswordVisibility) painterResource(id = R.drawable.close_eye)
                                else painterResource(id = R.drawable.open_eye),
                                contentDescription = "Toggle visibility"
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = if (oldPasswordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation()
                )
                Spacer(modifier = modifier.height(8.dp))
                Text(text = "New Password", fontSize = 16.sp)
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = {
                        newPassword = it
                    },
                    label = { Text("Enter new password") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = Color.Black,
                        focusedTextColor = Color.Black,
                        focusedLabelColor = Color.Black
                    ),
                    trailingIcon = {
                        IconButton(onClick = {
                            newPasswordVisibility = !newPasswordVisibility
                        }) {
                            Icon(
                                painter = if (newPasswordVisibility) painterResource(id = R.drawable.close_eye)
                                else painterResource(id = R.drawable.open_eye),
                                contentDescription = "Toggle visibility"
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = if (newPasswordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation()
                )
                Spacer(modifier = modifier.height(24.dp))

                androidx.compose.material.Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(BtnRed),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = BtnRed
                    )
                ) {
                    androidx.compose.material.Text(
                        text = "Save",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewChangePassword() {
    ChangePassword()
}
