package com.example.speedotransfer.profile


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speedotransfer.R
import com.example.speedotransfer.app_core.BottomNavigationBar
import com.example.speedotransfer.app_core.Favourite
import com.example.speedotransfer.app_core.MoreScreen
import com.example.speedotransfer.app_core.TransactionsActivity
import com.example.speedotransfer.ui.theme.GrayG100
import com.example.speedotransfer.ui.theme.GrayG200
import com.example.speedotransfer.ui.theme.GrayG40
import com.example.speedotransfer.ui.theme.LayerBackground1
import com.example.speedotransfer.ui.theme.PrimaryP300
import com.example.speedotransfer.ui.theme.PrimaryP50
import com.example.speedotransfer.ui.theme.SpeedoTransferTheme


class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeedoTransferTheme {
                Profile()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(name: String = "Asmaa Dosuky", modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val images = listOf(
        R.drawable.user,
        R.drawable.setting,
        R.drawable.history,
        R.drawable.favorite,
    )
    val titles = listOf(
        "Personal Information",
        "Setting",
        "Payment History",
        "My Favourite List",
    )
    val subtitles = listOf(
        "Your Information",
        "Change Your Settings",
        "View Your Transactions",
        "View your Favorites"
    )
    val onItemClicks = listOf<() -> Unit>(
        {
            val intent = Intent(context, ProfileInformationActivity::class.java)
            context.startActivity(intent)
        },
        {
            val intent = Intent(context, SettingActivity::class.java)
            context.startActivity(intent)
        },
        {
            val intent = Intent(context, TransactionsActivity::class.java)
            context.startActivity(intent)
        },
        {
            val intent = Intent(context, Favourite::class.java)
            context.startActivity(intent)
        },
    )
    val selectedItem by remember { mutableStateOf("More") }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text(
                        "Profile",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        val intent = Intent(context, MoreScreen::class.java)
                        context.startActivity(intent)
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.drop_down),
                            contentDescription = "Back button"
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
        Box(
            modifier = modifier
                .padding(innerPadding)
                .background(LayerBackground1)
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(48.dp)
                            .drawBehind {
                                drawCircle(color = GrayG40, radius = 64f)
                            }
                    ) {
                        Text(
                            text = getInitials(name),
                            color = GrayG100,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = name, fontSize = 20.sp, lineHeight = 24.sp)
                }
                Spacer(modifier = Modifier.height(32.dp))
                for (i in images.indices) {
                    ProfileItem(
                        image = images[i],
                        title = titles[i],
                        subtitle = subtitles[i],
                        onClick = onItemClicks[i]
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileItem(
    image: Int = R.drawable.user,
    title: String = "Personal Information",
    subtitle: String = "Your Information",
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickable(onClick = onClick)
    ) {
        Row(

        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .size(48.dp)
                    .background(color = PrimaryP50, shape = RoundedCornerShape(8.dp))
            ) {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(PrimaryP300),
                    modifier = modifier.size(24.dp)
                )

            }
            Spacer(modifier = modifier.width(16.dp))
            Column() {
                Text(text = title, fontSize = 16.sp, lineHeight = 24.sp)
                Text(text = subtitle, fontSize = 16.sp, lineHeight = 24.sp, color = GrayG100)
            }
        }
        Image(
            painter = painterResource(id = R.drawable.chevron),
            contentDescription = null,
            colorFilter = ColorFilter.tint(GrayG200),
            modifier = modifier.size(36.dp)
        )
    }
}


fun getInitials(name: String): String {
    val parts = name.split(" ")
    val firstInitial = parts.getOrNull(0)?.getOrNull(0)?.toString() ?: ""
    val secondInitial = parts.getOrNull(1)?.getOrNull(0)?.toString() ?: ""
    return (firstInitial + secondInitial).uppercase()
}

@Preview(showBackground = true)
@Composable
private fun PreviewProfile() {
    Profile()
}