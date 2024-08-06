package com.example.speedotransfer.add_card

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.speedotransfer.app_core.HomeScreen
import com.example.speedotransfer.ui.theme.BtnRed
import com.example.speedotransfer.ui.theme.Gray
import com.example.speedotransfer.ui.theme.SpeedoTransferTheme

class AddCardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeedoTransferTheme {
                AddCard()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCard(modifier: Modifier = Modifier) {
    val textFieldHeight = 56.dp
    var cardholderName by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    val context = LocalContext.current

    val isCardNumberValid = cardNumber.length == 16 && cardNumber.all { it.isDigit() }
    val isExpiryDateValid = expiryDate.length == 5 && expiryDate.matches(Regex("\\d{2}/\\d{2}"))
    val isCvvValid = cvv.length == 3 && cvv.all { it.isDigit() }
    val isFormValid = cardholderName.isNotBlank() && isCardNumberValid && isExpiryDateValid && isCvvValid

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
                        val intent = Intent(context, CurrencySelectionActivity::class.java)
                        context.startActivity(intent)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    TextButton(onClick = {
                        val intent = Intent(context, HomeScreen::class.java)
                        context.startActivity(intent)
                    }) {
                        Text(
                            text = "Cancel",
                            color = Gray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "Sign on your Speedo Transfer Account",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 30.sp,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 10.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Cardholder Name", modifier = Modifier.padding(bottom = 10.dp))
                OutlinedTextField(
                    value = cardholderName,
                    onValueChange = { cardholderName = it },
                    placeholder = {
                        Text(text = "Enter Cardholder Name")
                    },
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(textFieldHeight)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Card NO", modifier = Modifier.padding(bottom = 10.dp))
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() }) {
                            cardNumber = it
                        }
                    },
                    placeholder = {
                        Text(text = "Card NO")
                    },
                    isError = !isCardNumberValid && cardNumber.isNotEmpty(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(textFieldHeight)
                )
                if (!isCardNumberValid && cardNumber.isNotEmpty()) {
                    Text(
                        text = "Card number must be 16 digits",
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "MM/YY", modifier = Modifier.padding(bottom = 10.dp))
                    OutlinedTextField(
                        value = expiryDate,
                        onValueChange = {
                            if (it.length <= 5 && it.matches(Regex("\\d{0,2}/?\\d{0,2}"))) {
                                expiryDate = formatExpiryDate(it)
                            }
                        },
                        placeholder = {
                            Text(text = "MM/YY")
                        },
                        isError = !isExpiryDateValid && expiryDate.isNotEmpty(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        visualTransformation = ExpiryDateTransformation(),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(textFieldHeight)
                    )
                    if (!isExpiryDateValid && expiryDate.isNotEmpty()) {
                        Text(
                            text = "Expiry date must be in MM/YY format",
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "CVV", modifier = Modifier.padding(bottom = 10.dp))
                    OutlinedTextField(
                        value = cvv,
                        onValueChange = {
                            if (it.all { char -> char.isDigit() }) {
                                if (it.length <= 3) {
                                    cvv = it
                                }
                            }
                        },
                        placeholder = {
                            Text(text = "CVV")
                        },
                        isError = !isCvvValid && cvv.isNotEmpty(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(textFieldHeight)
                    )
                    if (!isCvvValid && cvv.isNotEmpty()) {
                        Text(
                            text = "CVV must be 3 digits",
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            androidx.compose.material.Button(
                onClick = {
                    val intent = Intent(context, ConnectingActivity::class.java)
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = BtnRed),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {

                    Text(
                        text = "Sign On",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500
                    )
            }
        }
    }
}

@Composable
fun ExpiryDateTransformation(): VisualTransformation {
    return VisualTransformation { text ->
        val trimmed = if (text.text.length >= 2) {
            text.text.substring(0, 2) + "/" + text.text.substring(2)
        } else {
            text.text
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 2 -> offset
                    offset <= 4 -> offset + 1
                    else -> 5
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 2 -> offset
                    offset <= 5 -> offset - 1
                    else -> 4
                }
            }
        }

        TransformedText(AnnotatedString(trimmed), offsetMapping)
    }
}

fun formatExpiryDate(input: String): String {
    val digits = input.filter { it.isDigit() }
    return when {
        digits.length <= 2 -> digits
        digits.length <= 4 -> digits.substring(0, 2) + "/" + digits.substring(2)
        else -> digits.substring(0, 2) + "/" + digits.substring(2, 4)
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAddCard() {
    SpeedoTransferTheme {
        AddCard()
    }
}
