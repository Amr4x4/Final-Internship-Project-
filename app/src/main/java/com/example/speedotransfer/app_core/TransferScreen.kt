package com.example.speedotransfer.app_core

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speedotransfer.R
import com.example.speedotransfer.app_core.HomeScreen
import com.example.speedotransfer.ui.theme.*

class TransferScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeedoTransferTheme {
                TransferScreenContent()
            }
        }
    }
}

@Composable
fun TransferScreenContent() {
    val selectedItem by remember { mutableStateOf("Transfer") }
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedItem = selectedItem,
                onItemSelected = { activityClass ->
                    val intent = Intent(context, activityClass)
                    context.startActivity(intent)
                }
            )
        }
    ) { paddingValues ->
        // Use Box to overlay the dialog on top of the scrollable content
        Box(modifier = Modifier.fillMaxSize()) {
            // Use LazyColumn to make content scrollable
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .background(LayerBackground1)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.drop_down),
                            contentDescription = "Back",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    val intent = Intent(context, HomeScreen::class.java)
                                    context.startActivity(intent)
                                }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "Transfer",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W500,
                            lineHeight = 30.sp,
                            color = TextColor,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

                item {
                    TransferScreenHeader(1)
                }

                item {
                    TransferScreenContentBody()
                }

                item {
                    TransferScreenForm()
                }
            }

            // The dialog is overlaid on top of the scrollable content
            var showCurrencyDialog by remember { mutableStateOf(false) }
            if (showCurrencyDialog) {
                CurrencySelectionDialog(onDismiss = { showCurrencyDialog = false }) { selectedCurrency ->
                    // Handle currency selection
                }
            }
        }
    }
}
@Composable
fun TransferScreenHeader(activeStep: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Stepper(activeStep = activeStep)
    }
}

@Composable
fun Stepper(activeStep: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Step(number = "01", label = "Amount", isActive = activeStep >= 1)
        Divider(isActive = activeStep >= 2)
        Step(number = "02", label = "Confirmation", isActive = activeStep >= 2)
        Divider(isActive = activeStep >= 3)
        Step(number = "03", label = "Payment", isActive = activeStep >= 3)
    }
}

@Composable
fun Step(number: String, label: String, isActive: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .background(Color.White, shape = CircleShape)
                .border(
                    width = 2.dp,
                    color = if (isActive) BtnRed else Color.Gray,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number,
                color = if (isActive) BtnRed else Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
        Text(
            text = label,
            color = if (isActive) Color.Black else Color.Gray,
            fontWeight = if (isActive) FontWeight.W500 else FontWeight.Normal,
            fontSize = 12.sp,
            maxLines = 1,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun Divider(isActive: Boolean) {
    Box(
        modifier = Modifier
            .height(1.5.dp)
            .width(85.dp)
            .background(if (isActive) BtnRed else Color.Gray)
    )
}

@Composable
fun TransferScreenContentBody() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "How much are you sending?",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Choose Currency",
            color = Color.Gray,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
    // Real body
    var sendAmount by remember { mutableStateOf(TextFieldValue("")) }
    var recipientAmount by remember { mutableStateOf(TextFieldValue("")) }
    var isUsdToEgp by remember { mutableStateOf(true) }
    val exchangeRate = 48.4220
    var showCurrencyDialog by remember { mutableStateOf(false) }
    var selectedCurrency by remember { mutableStateOf("USD") }

    fun calculateRecipientAmount(amount: String, rate: Double, isUsdToEgp: Boolean): String {
        val amountDouble = amount.toDoubleOrNull() ?: 0.0
        return if (isUsdToEgp) {
            (amountDouble * rate).toString()
        } else {
            (amountDouble / rate).toString()
        }
    }

    fun updateAmounts() {
        recipientAmount = TextFieldValue(
            calculateRecipientAmount(sendAmount.text, exchangeRate, isUsdToEgp)
        )
    }

    if (showCurrencyDialog) {
        CurrencySelectionDialog(onDismiss = { showCurrencyDialog = false }) { selectedCurrency ->
            isUsdToEgp = selectedCurrency == "USD"
            updateAmounts()
        }
    }

    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(300.dp) // Adjust height if needed
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "1 USD = 48.4220 EGP",
                color = Color.Black,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )
            Text(
                text = "Rate guaranteed (2h)",
                color = Color.Gray,
                fontWeight = FontWeight.W500,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            CurrencyField(
                label = "You Send",
                amount = sendAmount,
                onAmountChange = {
                    sendAmount = it
                    updateAmounts()
                },
                currency = if (isUsdToEgp) "USD" else "EGP",
                enabled = true,
                onCurrencyIconClick = { showCurrencyDialog = true }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .height(1.5.dp)
                    .fillMaxWidth()
                    .background(LightGray)
            )
            Spacer(modifier = Modifier.height(16.dp))
            CurrencyField(
                label = "Recipient Gets",
                amount = recipientAmount,
                onAmountChange = {
                    recipientAmount = it
                    updateAmounts()
                },
                currency = if (isUsdToEgp) "EGP" else "USD",

                enabled = true,
                onCurrencyIconClick = { showCurrencyDialog = true }
            )

            if (recipientAmount.text.toDoubleOrNull() ?: 0.0 > 5000 && isUsdToEgp) {
                Text(
                    text = "You can transfer money to other accounts with a limit of 5000 L.E. per transaction.",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
fun TransferScreenForm() {
    var country by remember { mutableStateOf("") }
    var recipientName by remember { mutableStateOf("") }
    var recipientAccount by remember { mutableStateOf("") }
    var isDialogVisible by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf("") }
    val countries = listOf(
        "United States", "United States", "United States",
        "United States", "United States", "United States", "United States"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recipient Information",
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                lineHeight = 24.sp
            )
            Row(
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    painter = painterResource(id = R.drawable.favorite),
                    contentDescription = "Favorite",
                    colorFilter = ColorFilter.tint(BtnRed),
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { isDialogVisible = true }
                )
                Spacer(modifier = Modifier.width(8.dp)) // Adding space between elements
                Text(
                    text = "Favorite",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    lineHeight = 21.sp,
                    color = BtnRed,
                    modifier = Modifier.clickable { isDialogVisible = true }
                )
                Spacer(modifier = Modifier.width(8.dp)) // Adding space between elements
                Image(
                    painter = painterResource(id = R.drawable.drop_down),
                    contentDescription = "Drop Down",
                    colorFilter = ColorFilter.tint(BtnRed),
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            isDialogVisible = true
                        }
                        .graphicsLayer(
                            scaleX = -1f // Flip horizontally
                        )
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        RecipientInfoField(
            label = "Recipient Name",
            value = recipientName,
            onValueChange = { recipientName = it },
            hintText = "Enter Recipient Name"
        )
        Spacer(modifier = Modifier.height(8.dp))
        RecipientInfoField(
            label = "Recipient Account",
            value = recipientAccount,
            onValueChange = { recipientAccount = it },
            hintText = "Enter Percipient Account Number"
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { /* Handle Continue action */ },
            colors = ButtonDefaults.buttonColors(backgroundColor = BtnRed),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = "Continue",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            )
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
fun CurrencyField(
    label: String,
    amount: TextFieldValue,
    onAmountChange: (TextFieldValue) -> Unit,
    currency: String,
    enabled: Boolean = true,
    onCurrencyIconClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            color = Color.Gray,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Currency Image
            Image(
                painter = painterResource(
                    id = when (currency) {
                        "USD" -> R.drawable.united_states // Replace with your actual image resource
                        "EGP" -> R.drawable.egypt_png // Replace with your actual image resource
                        else -> R.drawable.user // Default image if needed
                    }
                ),
                contentDescription = "$currency icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = currency,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )

            Spacer(modifier = Modifier.width(8.dp)) // Space between currency text and icon

            IconButton(onClick = onCurrencyIconClick) {
                Icon(
                    painter = painterResource(id = R.drawable.scroll_down), // Dropdown icon
                    contentDescription = "Select Currency"
                )
            }

            Spacer(modifier = Modifier.width(60.dp)) // Push OutlinedTextField to the end

            OutlinedTextField(
                value = amount,
                onValueChange = onAmountChange,
                placeholder = { Text(text = "0.0", color = PlaceholderColor) },
                singleLine = true,
                textStyle = TextStyle(
                    color = TextColor,
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp,
                    lineHeight = 30.sp,
                    textAlign = TextAlign.Left
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
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


        }

    }
}


@Composable
fun CurrencySelectionDialog(onDismiss: () -> Unit, onCurrencySelected: (String) -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Select Currency") },
        text = {
            Column {
                CurrencyOption("USD", R.drawable.united_states, onCurrencySelected)
                CurrencyOption("EGP", R.drawable.egypt_png, onCurrencySelected)
                // Add more currencies here if needed
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Composable
fun CurrencyOption(currency: String, imageRes: Int, onCurrencySelected: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCurrencySelected(currency) }
            .padding(vertical = 8.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "$currency icon",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = currency,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
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

@Composable
fun RecipientInfoField(
    label: String,
    hintText: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Text(
            text = label,
            color = TextColor,
            fontWeight = FontWeight.W400,
            fontSize = 16.sp,
            lineHeight = 24.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = hintText, color = PlaceholderColor) },
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
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewTransferScreen() {
    SpeedoTransferTheme {
        TransferScreenContent()
    }
}