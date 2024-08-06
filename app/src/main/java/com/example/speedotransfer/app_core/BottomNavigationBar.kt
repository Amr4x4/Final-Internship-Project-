package com.example.speedotransfer.app_core

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.speedotransfer.R
import com.example.speedotransfer.add_card.CurrencySelectionActivity
import com.example.speedotransfer.ui.theme.BtnRed

@Composable
fun BottomNavigationBar(
    selectedItem: String,
    onItemSelected: (Class<out ComponentActivity>) -> Unit
) {
    val items = listOf(
        NavItem("Home", R.drawable.home, HomeScreen::class.java),
        NavItem("Transfer", R.drawable.transfer, TransferScreen::class.java),
        NavItem("Transaction", R.drawable.history, TransactionScreen::class.java),
        NavItem("MyCard", R.drawable.cards, CurrencySelectionActivity::class.java),
        NavItem("More", R.drawable.more, MoreScreen::class.java)
    )

    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = BtnRed,
        elevation = (24.dp)
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Image(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.label,
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (selectedItem == item.label) BtnRed else Color.Gray
                    )
                },
                selected = selectedItem == item.label,
                onClick = { onItemSelected(item.screenClass) }
            )
        }
    }
}

data class NavItem(
    val label: String,
    val iconRes: Int,
    val screenClass: Class<out ComponentActivity>
)
