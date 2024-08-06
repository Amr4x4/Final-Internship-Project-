package com.example.speedotransfer.app_core

import com.example.speedotransfer.R
import com.example.speedotransfer.ui.theme.Danger300
import com.example.speedotransfer.ui.theme.DarkGreen
import com.example.speedotransfer.ui.theme.GrayG200
import com.example.speedotransfer.ui.theme.LightBink
import com.example.speedotransfer.ui.theme.LightGreen
import com.example.speedotransfer.ui.theme.PrimaryP300
import com.example.speedotransfer.ui.theme.PrimaryP50
import com.example.speedotransfer.ui.theme.SpeedoTransferTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



class TransactionsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeedoTransferTheme {
                Transactions()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Transactions(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text(
                        "Notifications",
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
        Box(modifier = modifier.padding(innerPadding)) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Your Last Transactions",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = modifier.padding(bottom = 8.dp))
                TransactionCard()
                TransactionCard(false)
                TransactionCard()

            }
        }
    }
}

@Composable
fun TransactionCard(status: Boolean = true, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .height(120.dp)
            .border(width = 0.5.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
            .clickable(
                onClick = {
                    // Handle click event
                }
            )
    ) {
        Row(
            modifier = modifier.padding(horizontal = 8.dp, vertical = 16.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .size(54.dp)
                    .background(color = PrimaryP50, shape = RoundedCornerShape(8.dp))
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.bank),
                    contentDescription = null,
                    tint = PrimaryP300,
                    modifier = modifier.size(36.dp)
                )
            }
            Spacer(modifier = modifier.width(16.dp))
            Column(
            ) {
                Text(text = "Ahmed Mohamed", fontSize = 14.sp, lineHeight = 12.sp)
                Text(
                    text = "Visa . Mater Card . 1234",
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = "Today 11:00 - Received",
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    fontWeight = FontWeight.Light
                )
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = "$1000",
                    lineHeight = 24.sp,
                    color = PrimaryP300,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.End,
            modifier = modifier.padding(horizontal = 8.dp, vertical = 16.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.chevron),
                contentDescription = null,
                tint = GrayG200,
                modifier = modifier.size(36.dp)
            )
            StatusLabel(status)
        }
    }
}

@Composable
fun StatusLabel(status: Boolean, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .width(if (status) 72.dp else 48.dp)
            .height(20.dp)
            .background(
                color = if (status) LightGreen else LightBink,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Text(
            text = if (status) "Successful" else "Failed",
            fontSize = 9.sp,
            color = if (status) DarkGreen else Danger300,
            lineHeight = 14.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTransactinos() {
        Transactions()
}