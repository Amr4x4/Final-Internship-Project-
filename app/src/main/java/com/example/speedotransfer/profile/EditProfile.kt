package com.example.speedotransfer.profile


import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speedotransfer.R
import com.example.speedotransfer.ui.theme.BtnRed
import com.example.speedotransfer.ui.theme.LayerBackground1
import com.example.speedotransfer.ui.theme.SpeedoTransferTheme

import java.util.*


class EditProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeedoTransferTheme {
                EditProfile()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfile() {
    val TextColor = Color.Black
    val BackgroundColor = Color.White
    var country by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var isDialogVisible by remember { mutableStateOf(false) }
    var isSuggestionVisible by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf("") }
    var dateOfBirthValid by remember { mutableStateOf(true) }
    val context = LocalContext.current

    val countries = listOf(
        "United States",
        "United States",
        "United States",
        "United States",
        "United States",
        "United States",
        "United States"
    )
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text(
                        "Edit Profile",
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
            modifier = Modifier
                .fillMaxSize()
                .background(color = BackgroundColor)
                .padding(innerPadding)
                .background(LayerBackground1)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    var cardholderName by remember { mutableStateOf("") }
                    Text(text = "Full Name")
                    OutlinedTextField(
                        value = cardholderName,
                        onValueChange = { cardholderName = it },
                        placeholder = {
                            Text(text = "Enter Cardholder Name")
                        },
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    var cardNumber by remember { mutableStateOf("") }
                    Text(text = "Email")
                    OutlinedTextField(
                        value = cardNumber,
                        onValueChange = { cardNumber = it },
                        placeholder = {
                            Text(text = "Enter Email")
                        },
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Country",
                    color = TextColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.align(Alignment.Start)
                )
                Box {
                    Column {
                        OutlinedTextField(
                            value = country,
                            onValueChange = {
                                country = it
                                isSuggestionVisible = it.isNotEmpty()
                            },
                            placeholder = {
                                Text(
                                    text = "Select your country",
                                )
                            },
                            trailingIcon = {
                                Image(painter = painterResource(id = R.drawable.scroll_down),
                                    contentDescription = "Scroll to choose your country",
                                    modifier = Modifier
                                        .size(24.dp, 24.dp)
                                        .clickable { isDialogVisible = true })
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),

                            )
                        if (isSuggestionVisible) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp)
                                    .background(Color.White)
                                    .padding(top = 8.dp)
                            ) {
                                items(countries.filter {
                                    it.contains(
                                        country, ignoreCase = true
                                    )
                                }) { suggestion ->
                                    Text(text = suggestion,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                country = suggestion
                                                isSuggestionVisible = false
                                            }
                                            .padding(16.dp))
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Date Of Birth",
                    color = TextColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.align(Alignment.Start)
                )
                OutlinedTextField(
                    value = dateOfBirth,
                    onValueChange = {
                        dateOfBirth = formatDateString(it)
                        dateOfBirthValid = validateDateOfBirth(it)
                    },
                    placeholder = { Text(text = "DD/MM/YYYY") },
                    trailingIcon = {
                        Image(painter = painterResource(id = R.drawable.date),
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    showDatePicker(context) { date ->
                                        dateOfBirth = date
                                        dateOfBirthValid = validateDateOfBirth(date)
                                    }
                                })
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                if (!dateOfBirthValid) {
                    Text(
                        text = "Unaccepted Date",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(top = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
                androidx.compose.material.Button(
                    onClick = {  },
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

            if (isDialogVisible) {
                CountryPickerDialog(countries = countries,
                    selectedCountry = selectedCountry,
                    onCountrySelected = { selectedCountry ->
                        country = selectedCountry
                        isDialogVisible = false
                    },
                    onDismissRequest = { isDialogVisible = false })
            }
        }
    }
}


@Composable
fun CountryPickerDialog(
    countries: List<String>,
    selectedCountry: String,
    onCountrySelected: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(onClick = onDismissRequest), contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(515.dp)
                .background(Color.White, RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxHeight()
            ) {
                items(countries) { country ->
                    val isSelected = country == selectedCountry
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCountrySelected(country) }
                        .background(if (isSelected) Color.LightGray else Color.Transparent)
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.united_states),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = country)
                        if (isSelected) {
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                painter = painterResource(id = R.drawable.united_states),
                                contentDescription = null,
                                tint = Color.Green,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

fun formatDateString(input: String): String {
    val digitsOnly = input.filter { it.isDigit() }
    return buildString {
        for (i in digitsOnly.indices) {
            append(digitsOnly[i])
            if (i == 1 || i == 3) append('/')
        }
    }.take(10)
}

fun validateDateOfBirth(dateString: String): Boolean {
    return try {
        val parts = dateString.split('/')
        if (parts.size != 3) return false
        val day = parts[0].toIntOrNull() ?: return false
        val month = parts[1].toIntOrNull() ?: return false
        val year = parts[2].toIntOrNull() ?: return false
        if (year !in 1900..Calendar.getInstance().get(Calendar.YEAR)) return false
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day)
        calendar.timeInMillis < Calendar.getInstance().timeInMillis
    } catch (e: Exception) {
        false
    }
}

fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog =
        DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
            onDateSelected(
                String.format(
                    "%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear
                )
            )
        }, year, month, day)

    datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Cancel") { dialog, _ ->
        dialog.dismiss()
    }

    datePickerDialog.show()
}

@Preview(showBackground = true)
@Composable
fun UserContinueSignUpPreview() {
        EditProfile()
}