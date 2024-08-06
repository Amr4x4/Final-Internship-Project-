package com.example.speedotransfer.add_card

import android.content.Intent
import com.example.speedotransfer.data_source.DataSource
import com.example.speedotransfer.ui.theme.SpeedoTransferTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.speedotransfer.R
import com.example.speedotransfer.app_core.HomeScreen
import com.example.speedotransfer.app_core.MoreScreen
import com.example.speedotransfer.data_source.Currency
import com.example.speedotransfer.ui.theme.BtnRed
import com.example.speedotransfer.ui.theme.Gray
import com.example.speedotransfer.ui.theme.LightRed

class CurrencySelectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeedoTransferTheme {
                CurrencySelection()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencySelection(modifier: Modifier = Modifier) {
    val currencies = DataSource().getCurrencies()
    var selectedIndex by rememberSaveable { mutableStateOf(-1) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text(
                        "Select Currency",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        val intent = Intent(context, MoreScreen::class.java)
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
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 32.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .selectableGroup()
                ) {
                    items(currencies) { currency ->
                        val index = currencies.indexOf(currency)
                        CurrencyItem(
                            currency = currency,
                            isSelected = selectedIndex == index,
                            onClick = {
                                // Toggle selection
                                selectedIndex = if (selectedIndex == index) -1 else index
                            }
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp, vertical = 64.dp) // Added bottom padding
            ) {
                androidx.compose.material.Button(
                    onClick = {
                        val intent = Intent(context, AddCardActivity::class.java)
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = BtnRed),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {

                    Text(
                        text = "Get Started",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500
                    )
                }
            }
        }
    }
}

@Composable
fun CurrencyItem(
    currency: Currency,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = isSelected,
                onClick = onClick,
                role = Role.RadioButton
            )
            .background(if (isSelected) Color.Gray else Color.Transparent)
            .padding(vertical = 10.dp)
    ) {
        Image(
            painter = painterResource(currency.picture),
            contentDescription = currency.name,
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)
        )
        Text(text = currency.name, modifier = Modifier.weight(1f))
        if (isSelected) {
            Image(
                painter = painterResource(R.drawable.check_mark),
                contentDescription = "Checked",
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .size(20.dp)
            )
        }
    }
    HorizontalDivider(thickness = 1.dp)
}

@Preview(showBackground = true)
@Composable
private fun PrevCurrencySelection() {
    CurrencySelection()
}

