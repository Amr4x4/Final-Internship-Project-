package com.example.speedotransfer.app_core

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speedotransfer.R
import com.example.speedotransfer.ui.theme.BtnRed
import com.example.speedotransfer.ui.theme.FavCardBack
import com.example.speedotransfer.ui.theme.Gray
import com.example.speedotransfer.ui.theme.GrayG40
import com.example.speedotransfer.ui.theme.LayerBackground1
import com.example.speedotransfer.ui.theme.LightGray
import com.example.speedotransfer.ui.theme.SpeedoTransferTheme
import com.example.speedotransfer.ui.theme.TextColor
import kotlinx.coroutines.launch

class Favourite : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeedoTransferTheme {
                FavouriteScreen()
            }
        }
    }
}

data class FavouriteItemData(val name: String, val accountNumber: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteScreen(modifier: Modifier = Modifier) {
    var isEditSheetOpen by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var favouriteItems by remember { mutableStateOf(listOf(
        FavouriteItemData("Asmaa Dosuky", "11117890"),
        FavouriteItemData("John Doe", "22227890"),
        FavouriteItemData("Jane Smith", "33337890")
    )) }
    var selectedItem by remember { mutableStateOf<FavouriteItemData?>(null) }
    var recipientAccount by remember { mutableStateOf("") }
    var recipientName by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text(
                        "Favourite",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        val intent = Intent(context, MoreScreen::class.java)
                        context.startActivity(intent)
                    }) {
                        Image(painter = painterResource(id = R.drawable.drop_down),
                            contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .background(LayerBackground1)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Your favourite list",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                modifier = modifier.padding(16.dp)
            )

            favouriteItems.forEach { item ->
                FavouriteItem(
                    name = item.name,
                    accountNumber = item.accountNumber,
                    onEditClick = {
                        selectedItem = item
                        recipientName = item.name
                        recipientAccount = item.accountNumber
                        isEditSheetOpen = true
                        scope.launch { sheetState.show() }
                    },
                    onDeleteClick = {
                        favouriteItems = favouriteItems.filter { it != item }
                    }
                )
            }

            if (isEditSheetOpen) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = {
                        isEditSheetOpen = false
                        scope.launch { sheetState.hide() }
                    },
                    containerColor = Color.White,
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = modifier.padding(16.dp)
                    ) {
                        Text(text = "Recipient Account", modifier = Modifier.padding(bottom = 10.dp))
                        OutlinedTextField(
                            value = recipientAccount,
                            onValueChange = { recipientAccount = it },
                            placeholder = {
                                Text(text = "Enter Recipient Account")
                            },
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        )
                        Spacer(modifier = modifier.height(8.dp))
                        Text(text = "Recipient Name", modifier = Modifier.padding(bottom = 10.dp))
                        OutlinedTextField(
                            value = recipientName,
                            onValueChange = { recipientName = it },
                            placeholder = {
                                Text(text = "Enter Recipient Name")
                            },
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        )
                        Spacer(modifier = modifier.height(32.dp))
                        Button(
                            onClick = {
                                selectedItem?.let { item ->
                                    favouriteItems = favouriteItems.map {
                                        if (it == item) {
                                            FavouriteItemData(recipientName, recipientAccount)
                                        } else {
                                            it
                                        }
                                    }
                                    isEditSheetOpen = false
                                    scope.launch { sheetState.hide() }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(6.dp),
                        ) {
                            Text(text = "Save")
                        }
                        Spacer(modifier = modifier.height(135.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun FavouriteItem(
    name: String,
    accountNumber: String,
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = FavCardBack)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bank),
            contentDescription = "Bank Icon",
            modifier = Modifier
                .size(32.dp)
                .drawBehind {
                    drawCircle(color = GrayG40, radius = 64f)
                }
                .weight(0.75f)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(2f)
        ) {
            Text(text = name)
            Text(text = "Account ${maskString(accountNumber)}", color = TextColor)
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(0.75f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.edit),
                contentDescription = "edit",
                tint = Gray,
                modifier = Modifier
                    .clickable { onEditClick() }
                    .size(18.dp)

            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                painter = painterResource(id = R.drawable.delete),
                contentDescription = "delete",
                tint = BtnRed,
                modifier = Modifier
                    .clickable { onDeleteClick() }
                    .size(18.dp)
            )
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

fun maskString(input: String): String {
    val length = input.length
    return if (length <= 4) {
        input
    } else {
        "x".repeat(length - 4) + input.takeLast(4)
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewFavouriteScreen() {
        FavouriteScreen()
}
