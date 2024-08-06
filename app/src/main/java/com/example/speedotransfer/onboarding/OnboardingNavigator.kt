package com.example.speedotransfer.onboarding

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speedotransfer.MainActivity
import com.example.speedotransfer.R
import com.example.speedotransfer.ui.theme.DarkRed
import com.example.speedotransfer.ui.theme.LightRed
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

data class OnboardingPage(
    val title: String,
    val description: String,
    val imageRes: Int
)

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingPager() {
    val pagerState = rememberPagerState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val pages = listOf(
        OnboardingPage(
            stringResource(id = R.string.amot),
            stringResource(id = R.string.amot_content),
            R.drawable.amont
        ),
        OnboardingPage(
            stringResource(id = R.string.confirmation),
            stringResource(id = R.string.confirmation_content),
            R.drawable.confirmation
        ),
        OnboardingPage(
            stringResource(id = R.string.payment),
            stringResource(id = R.string.payment_content),
            R.drawable.payment
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HorizontalPager(
                count = pages.size,
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                OnboardingScreen(
                    title = pages[page].title,
                    description = pages[page].description,
                    imageRes = pages[page].imageRes,
                    pageIndicator = {
                        HorizontalPagerIndicator(
                            pagerState = pagerState,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 16.dp, bottom = 8.dp),
                            activeColor = DarkRed,
                            inactiveColor = LightRed,
                            indicatorWidth = 16.dp,
                            indicatorHeight = 16.dp,
                            indicatorShape = CircleShape
                        )
                    },
                    onNextClick = {
                        if (page == pages.size - 1) {
                            completeOnboarding(context)
                        } else {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page + 1)
                            }
                        }
                    }
                )
            }
        }

        // Skip button
        Text(
            text = "Skip",
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .clickable {
                    completeOnboarding(context)
                }
        )
    }
}



fun completeOnboarding(context: Context) {
    val sharedPreferences = context.getSharedPreferences("app_prefs", MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putBoolean("isFirstLaunch", false)
        apply()
    }
    // Navigate to the main activity
    context.startActivity(Intent(context, MainActivity::class.java))
    if (context is Activity) {
        context.finish()
    }
}
