package com.hantash.weather_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hantash.weather_app.ui.navigation.ScreenNavigation
import com.hantash.weather_app.ui.theme.WeatherappcomposeTheme
import com.hantash.weather_app.utils.RequestPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherApp {
//                RequestPermission(
//                    permission =  Manifest.permission.CAMERA,
//                    rationaleMessage = "Location Permission is required in order to use this app."
//                ) { isGranted ->
//                    if (isGranted) {
//
//                    }
//                }
                ScreenNavigation()
            }
        }
    }
}

@Composable
fun WeatherApp(content: @Composable () -> Unit) {
    WeatherappcomposeTheme {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherApp {  }
}