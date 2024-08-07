package com.example.speedotransfer.app_core

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.speedotransfer.R
import com.example.speedotransfer.profile.ProfileActivity
import com.example.speedotransfer.sign_up.SignUpFirstScreen
import com.example.speedotransfer.ui.theme.BtnRed
import com.example.speedotransfer.ui.theme.ClickableGray
import com.example.speedotransfer.ui.theme.LayerBackground1
import com.example.speedotransfer.ui.theme.LayerBackground2
import com.example.speedotransfer.ui.theme.SpeedoTransferTheme
import com.example.speedotransfer.ui.theme.Yellow

class MoreScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpeedoTransferTheme {
                MoreList()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreList(modifier: Modifier = Modifier) {
    var isHelpSheetOpen by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val context = LocalContext.current
    val selectedItem by remember { mutableStateOf("More") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text(
                        "More",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                modifier = Modifier.background(LayerBackground1),
                navigationIcon = {
                    IconButton(onClick = {
                        val intent = Intent(context, HomeScreen::class.java)
                        context.startActivity(intent)
                    }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },

        bottomBar = {
            BottomNavigationBar(
                selectedItem = selectedItem,
                onItemSelected = { activityClass ->
                    val intent = Intent(context, activityClass)
                    context.startActivity(intent)
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
                    .background(LayerBackground1)
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                val images = listOf(
                    R.drawable.website,
                    R.drawable.favorite,
                    R.drawable.user,
                    R.drawable.support,
                    R.drawable.logout
                )
                val descriptions = listOf(
                    "Website",
                    "Favourite",
                    "Profile",
                    "Help",
                    "Logout"
                )
                val onItemClicks = listOf<() -> Unit>(
                    {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("https://www.bankmaster.com")
                        }
                    },
                    {
                        val intent = Intent(context, Favourite::class.java)
                        context.startActivity(intent)
                    },
                    {
                        val intent = Intent(context, ProfileActivity::class.java)
                        context.startActivity(intent)
                    },
                    { isHelpSheetOpen = true },
                    {
                        val intent = Intent(context, SignUpFirstScreen::class.java).apply {
                            // Clear the back stack and start new task
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        context.startActivity(intent)
                    }
                )
                for (i in images.indices) {
                    MoreItem(
                        image = images[i],
                        description = descriptions[i],
                        onClick = onItemClicks[i]
                    )
                }
            }

            if (isHelpSheetOpen) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable { isHelpSheetOpen = false }
                ) {}
            }

            if (isHelpSheetOpen) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = { isHelpSheetOpen = false },
                    containerColor = Color.White
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        HelpItem(
                            icon = R.drawable.email,
                            text = "Send Email",
                            onClick = {
                                val intent = Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse("mailto:help@speedo.com")
                                }
                                context.startActivity(intent)
                            },
                        )
                        Spacer(modifier = modifier.width(32.dp))
                        HelpItem(
                            icon = R.drawable.call,
                            text = "Call Us",
                            onClick = {
                                val intent = Intent(Intent.ACTION_DIAL).apply {
                                    data = Uri.parse("tel:00000")
                                }
                                context.startActivity(intent)
                            },
                        )
                    }
                    Spacer(modifier = modifier.height(55.dp))
                }
            }
        }
    }
}


@Composable
fun MoreItem(image: Int, description: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable(onClick = onClick)
        ) {
            Row {
                Icon(
                    painter = painterResource(id = image),
                    contentDescription = description,
                    Modifier.size(28.dp),
                    tint = ClickableGray
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = description, color = ClickableGray)
            }
            if (description != "Logout")
                Icon(
                    painter = painterResource(id = R.drawable.chevron),
                    contentDescription = "chevron",
                    tint = ClickableGray
                )
        }
        if (description != "Logout")
            HorizontalDivider(
                thickness = 1.dp,
                color = ClickableGray,
            )
    }
}

@Composable
fun HelpItem(icon: Int, text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(140.dp)
            .width(120.dp)
            .clickable(onClick = onClick)
            .shadow(2.dp, RoundedCornerShape(10.dp), clip = false)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.align(Alignment.Center)
        ) {
            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(LayerBackground2)
                    .width(50.dp)
                    .height(50.dp),
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = text,
                    modifier = modifier
                        .align(Alignment.Center)
                        .size(22.dp),
                    colorFilter = ColorFilter.tint(BtnRed)
                )
            }
            Text(
                text = text, modifier = modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMoreItem() {
    SpeedoTransferTheme {
        MoreList()
    }
}
