package com.example.speedotransfer.profile


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.sp
import com.example.speedotransfer.R
import com.example.speedotransfer.ui.theme.GrayG100
import com.example.speedotransfer.ui.theme.GrayG40
import com.example.speedotransfer.ui.theme.LayerBackground1
import com.example.speedotransfer.ui.theme.SpeedoTransferTheme


class ProfileInformationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeedoTransferTheme {
                ProfileInformation()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileInformation(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val titles = listOf(
        "Full Name",
        "Email",
        "Date Of Birth",
        "Country",
        "Bank Account"
    )

    //for testing
    val values = listOf(
        "Asmaa Dosuky",
        "Asmaa@gmail.com",
        "12/01/2000",
        "Egypt",
        "1234xxxx"
    )
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text(
                        "Profile Infromation",
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
        Box(modifier = modifier.padding(innerPadding).background(LayerBackground1)) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                for (i in titles.indices) {
                    ProfileInformationItem(titles[i], values[i])
                }
            }
        }
    }
}

@Composable
fun ProfileInformationItem(
    title: String = "Full Name",
    value: String = "Asmaa Dosuky",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.height(88.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = title, fontSize = 16.sp, lineHeight = 24.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = value, color = GrayG100, fontSize = 16.sp, lineHeight = 24.sp)
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        HorizontalDivider(
            color = GrayG40,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewProfileInformation() {

    ProfileInformation()

}