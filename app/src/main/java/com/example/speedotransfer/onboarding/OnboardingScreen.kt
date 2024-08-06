package com.example.speedotransfer.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speedotransfer.ui.theme.BtnRed
import com.example.speedotransfer.ui.theme.White

@Composable
fun OnboardingScreen(
    title: String,
    description: String,
    imageRes: Int,
    pageIndicator: @Composable () -> Unit,
    onNextClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()

        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = description,
                modifier = Modifier
                    .height(303.dp)
                    .width(350.dp)
                    .fillMaxWidth()

            )
            Spacer(modifier = Modifier.height(16.dp))
            pageIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onNextClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BtnRed,
                    contentColor = Color.White
                )
            ) {
                Text("Next")
            }

        }
    }
}
