package com.example.speedotransfer.app_core

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speedotransfer.R
import com.example.speedotransfer.add_card.CurrencySelectionActivity
import com.example.speedotransfer.api.UserViewModel
import com.example.speedotransfer.ui.theme.*

class HomeScreen : ComponentActivity() {
    private var backButtonCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userViewModel: UserViewModel by viewModels()

        setContent {
            SpeedoTransferTheme {
                val userName by userViewModel.userName.collectAsState()
                val balance by userViewModel.balance.collectAsState()
                HomeScreenNavigation(
                    userName = userName,
                    balance = balance,
                    onBackPressed = { backPressed() }
                )
            }
        }
    }

    private fun backPressed() {
        if (backButtonCount >= 1) {
            // Move to home screen
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show()
            backButtonCount++
        }
    }
}

@Composable
fun HomeScreenNavigation(
    userName: String?,
    balance: Double?,
    onBackPressed: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val selectedItem by remember { mutableStateOf("Home") }
    val transactions = listOf(
        Transaction("Ahmed Mohamed", "Visa", "Master Card . 1234", "Today 11:00", "Received", "1000"),
        Transaction("Ahmed Mohamed", "Visa", "Master Card . 1234", "Today 11:00", "Received", "1000"),
        Transaction("Ahmed Mohamed", "Visa", "Master Card . 1234", "Today 11:00", "Received", "1000"),
        Transaction("Ahmed Mohamed", "Visa", "Master Card . 1234", "Today 11:00", "Received", "1000"),
        Transaction("Ahmed Mohamed", "Visa", "Master Card . 1234", "Today 11:00", "Received", "1000")
    )

    // Handle back press
    BackHandler {
        onBackPressed()
    }

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
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(LayerBackground1)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 50.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = userName?.take(2) ?: "AD",
                        fontWeight = FontWeight.W600,
                        color = Gray,
                        fontSize = 20.sp,
                        lineHeight = 30.sp
                    )
                }
                Column {
                    Text(
                        text = "Welcome back,",
                        color = BtnRed,
                        fontWeight = FontWeight.W400,
                        fontSize = 14.sp,
                        lineHeight = 21.sp
                    )
                    Text(
                        text = userName ?: "Asmaa Dosuky",
                        color = TextColor,
                        fontWeight = FontWeight.W500,
                        fontSize = 16.sp,
                        lineHeight = 24.sp
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.notification),
                    contentDescription = "Notification",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            // Handle click action here
                        }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                backgroundColor = BtnRed,
                contentColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(123.dp),
                elevation = 8.dp
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 13.dp, vertical = 25.dp)
                ) {
                    Text(
                        text = "Current Balance",
                        color = White,
                        fontWeight = FontWeight.W500,
                        fontSize = 16.sp,
                        lineHeight = 24.sp
                    )
                    Text(
                        text = balance?.let { "$$it" } ?: "$2,85,856.20",
                        color = White,
                        fontWeight = FontWeight.W600,
                        fontSize = 26.sp,
                        lineHeight = 42.sp
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(141.dp)
                    .padding(16.dp),
                elevation = 8.dp,
                backgroundColor = White
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Service",
                        fontWeight = FontWeight.W500,
                        lineHeight = 24.sp,
                        fontSize = 16.sp,
                        color = LightTextColor,
                        textAlign = TextAlign.Start,
                    )
                    Row {
                        IconWithText(
                            iconRes = R.drawable.transfer,
                            contentDescription = "Transfer",
                            text = "Transfer",
                            onClick = {
                                val intent = Intent(context, TransferScreen::class.java)
                                context.startActivity(intent)
                            }
                        )
                        Spacer(modifier = Modifier.width(16.dp))

                        IconWithText(
                            iconRes = R.drawable.history,
                            contentDescription = "Transactions",
                            text = "Transactions",
                            onClick = {
                                val intent = Intent(context, TransactionsActivity::class.java)
                                context.startActivity(intent)
                            }
                        )
                        Spacer(modifier = Modifier.width(32.dp))

                        IconWithText(
                            iconRes = R.drawable.cards,
                            contentDescription = "Cards",
                            text = "Cards",
                            onClick = {
                                val intent = Intent(context, CurrencySelectionActivity::class.java)
                                context.startActivity(intent)
                            }
                        )
                        Spacer(modifier = Modifier.width(32.dp))

                        IconWithText(
                            iconRes = R.drawable.account,
                            contentDescription = "Account",
                            text = "Account",
                            onClick = {
                                val intent = Intent(context, MoreScreen::class.java)
                                context.startActivity(intent)
                            }
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LayerBackground2)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Recent transactions",
                        fontWeight = FontWeight.W500,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        color = TextColor
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "View all",
                        fontWeight = FontWeight.W500,
                        textAlign = TextAlign.End,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        color = ClickableGray,
                        modifier = Modifier.clickable { showDialog = true }
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        //.padding(horizontal = 16.dp)
                ) {
                    items(transactions.take(3)) { transaction ->
                        TransactionCard(transaction)
                    }
                }
            }
        }
    }

    if (showDialog) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(10.dp)  // Padding around the dialog content
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = "All Transactions",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(transactions) { transaction ->
                        TransactionCard(transaction)
                    }
                }
                TextButton(
                    onClick = { showDialog = false },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Close")
                }
            }
        }
    }
}

@Composable
fun TransactionCard(transaction: Transaction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .height(80.dp),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(61.dp)
                    .width(64.dp)
                    .background(LightRed2),
                Alignment.Center
            ){
            Image(
                painter = painterResource(id = R.drawable.visa),
                contentDescription = null,
                modifier = Modifier.size(32.dp, 24.dp)
            )}
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = transaction.name,
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp,
                    lineHeight = 21.sp
                )
                Text(
                    text = "${transaction.cardType} • ${transaction.cardNumber}",
                    color = Color.Gray,
                    fontWeight = FontWeight.W400,
                    fontSize = 12.sp
                )
                Text(
                    text = "${transaction.date} • ${transaction.type}",
                    color = Color.Gray,
                    fontWeight = FontWeight.W400,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "\$${transaction.amount}",
                color = BtnRed,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                maxLines = 1
            )
        }
    }
}

data class Transaction(
    val name: String,
    val cardType: String,
    val cardNumber: String,
    val date: String,
    val type: String,
    val amount: String
)

@Composable
fun IconWithText(
    @DrawableRes iconRes: Int,
    contentDescription: String,
    text: String,
    onClick: () -> Unit,
    fontSize: TextUnit = 12.sp,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size(60.dp, 85.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            modifier = Modifier.size(30.dp),
            colorFilter = ColorFilter.tint(Yellow)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = FontWeight.W500,
            maxLines = 1,
            lineHeight = 21.sp,
            color = TextColor
        )
    }
}

