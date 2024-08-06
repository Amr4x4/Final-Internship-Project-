package com.example.speedotransfer.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.speedotransfer.R
import com.example.speedotransfer.ui.theme.LayerBackground1
import com.example.speedotransfer.ui.theme.SpeedoTransferTheme


class SettingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeedoTransferTheme {
                Setting()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Setting(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val images = listOf(
        R.drawable.password_outline,
        R.drawable.edit,
    )
    val titles = listOf(
        "Change Password",
        "Edit Profile",
    )
    val subtitles = listOf(
        "Change Password",
        "Change Your Information",
    )
    val onItemClicks = listOf<() -> Unit>(
        {

        },
        {
            val intent = Intent(context, EditProfileActivity::class.java)
            context.startActivity(intent)
        }
    )
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text(
                        "Setting",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        val intent = Intent(context, ProfileActivity::class.java)
                        context.startActivity(intent)
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.drop_down),
                            contentDescription = "Back button"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding).background(LayerBackground1)){
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
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


@Preview(showBackground = true)
@Composable
private fun PreviewSetting() {
        Setting()
}