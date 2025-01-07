package com.hantash.weather_app.ui.screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hantash.weather_app.R
import com.hantash.weather_app.ui.navigation.EnumScreen
import kotlinx.coroutines.delay

@Preview
@Composable
fun SplashScreen(navController: NavController? = null) {
    val scale = remember {
        Animatable(initialValue = 0f)
    }

    LaunchedEffect(key1 = true, block = {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 800, easing = { OvershootInterpolator(7f).getInterpolation(it) }
            )
        )
        delay(1000)
        navController?.navigate(EnumScreen.MAIN_SCREEN.name)
    })

    Box(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.Center)
                .scale(scale.value),
            color = Color.Transparent,
            shape = CircleShape,
            border = BorderStroke(width = 2.dp, color = Color.LightGray),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier.size(90.dp),
                    painter = painterResource(R.drawable.ic_sun),
                    contentDescription = "Icon Sun",
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = "Find the Sun?",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}