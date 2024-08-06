package com.example.speedotransfer.sign_up

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
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speedotransfer.R
import com.example.speedotransfer.app_core.HomeScreen
import com.example.speedotransfer.ui.theme.*
import java.util.*

class SignUpSecondScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeedoTransferTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    UserContinueSignUp()
                }
            }
        }
    }
}

@Composable
fun UserContinueSignUp() {
    var country by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var isDialogVisible by remember { mutableStateOf(false) }
    var isSuggestionVisible by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf("") }
    var dateOfBirthValid by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val allFieldsValid = remember(country, dateOfBirth, dateOfBirthValid) {
        country.isNotBlank() && dateOfBirth.isNotBlank() && dateOfBirthValid
    }
    val countries = listOf(
        "United States", "United States", "United States",
        "United States", "United States", "United States", "United States"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
    ) {
        Image(
            painter = painterResource(id = R.drawable.drop_down),
            contentDescription = "Back",
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
                .size(24.dp, 24.dp)
                .clickable {
                    val intent = Intent(context, SignUpFirstScreen::class.java)
                    context.startActivity(intent)
                }
        )

        Spacer(modifier = Modifier.height(64.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
                color = TextColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.W600,
                lineHeight = 30.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(67.dp))

            Text(
                text = "Welcome to Banque Misr!",
                color = TextColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.W600,
                lineHeight = 36.sp,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Let’s Complete your Profile",
                color = LightTextColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 24.sp,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Country Field
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
                                color = PlaceholderColor
                            )
                        },
                        trailingIcon = {
                            Image(
                                painter = painterResource(id = R.drawable.scroll_down),
                                contentDescription = "Scroll to choose your country",
                                modifier = Modifier
                                    .size(24.dp, 24.dp)
                                    .clickable { isDialogVisible = true }
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
                    if (isSuggestionVisible) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .background(Color.White)
                                .padding(top = 8.dp)  // Add padding to position suggestions below the text field
                        ) {
                            items(countries.filter {
                                it.contains(
                                    country,
                                    ignoreCase = true
                                )
                            }) { suggestion ->
                                Text(
                                    text = suggestion,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            country = suggestion
                                            isSuggestionVisible = false
                                        }
                                        .padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Date Of Birth Field
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
                placeholder = { Text(text = "DD/MM/YYYY", color = PlaceholderColor) },
                trailingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.date),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                showDatePicker(context) { date ->
                                    dateOfBirth = date
                                    dateOfBirthValid = validateDateOfBirth(date)
                                }
                            }
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
                ),
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
            Button(
                onClick = {
                    if (allFieldsValid) {
                        val intent = Intent(context, HomeScreen::class.java)
                        context.startActivity(intent)
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
                    text = "Continue",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }

        if (isDialogVisible) {
            CountryPickerDialog(
                countries = countries,
                selectedCountry = selectedCountry,
                onCountrySelected = { selectedCountry ->
                    country = selectedCountry
                    isDialogVisible = false
                },
                onDismissRequest = { isDialogVisible = false }
            )
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
            .clickable(onClick = onDismissRequest),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(515.dp)  // Set the dialog height to 350dp
                .background(Color.White, RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxHeight()
            ) {
                items(countries) { country ->
                    val isSelected = country == selectedCountry
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCountrySelected(country) }
                            .background(if (isSelected) Color.LightGray else Color.Transparent)
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.united_states),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = country, color = TextColor)
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
                    "%02d/%02d/%04d",
                    selectedDay,
                    selectedMonth + 1,
                    selectedYear
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
    UserContinueSignUp()
}