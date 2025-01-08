package com.hantash.weather_app.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hantash.weather_app.data.api.ResultAPI
import com.hantash.weather_app.model.WeatherResponse
import com.hantash.weather_app.ui.components.BaseAppBar
import com.hantash.weather_app.ui.navigation.EnumScreen
import com.hantash.weather_app.viewmodel.WeatherViewModel

@Composable
fun MainScreen(navController: NavController) {
    val viewModel = hiltViewModel<WeatherViewModel>()

    val weatherData = produceState<ResultAPI<WeatherResponse, Boolean, Exception>>(
        initialValue = ResultAPI(isLoading = true)
    ) {
        value = viewModel.fetchCurrentWeather("California")
    }.value

    if (weatherData.isLoading) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        ScreenContent(navController, weatherData.data)
    }
}

@Preview
@Composable
private fun ScreenContent(navController: NavController? = null, weatherResponse: WeatherResponse? = null) {
    val title = "${weatherResponse?.city?.name}, ${weatherResponse?.city?.country}"
    Scaffold (
        topBar = {
            BaseAppBar(
                title = title,
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {  }
        }
    )
}








