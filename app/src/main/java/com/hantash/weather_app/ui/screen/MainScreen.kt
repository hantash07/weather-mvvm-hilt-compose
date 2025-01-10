package com.hantash.weather_app.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hantash.weather_app.R
import com.hantash.weather_app.data.api.ResultAPI
import com.hantash.weather_app.model.Item0
import com.hantash.weather_app.model.WeatherResponse
import com.hantash.weather_app.ui.components.AppImage
import com.hantash.weather_app.ui.components.AppTextIcon
import com.hantash.weather_app.ui.components.BaseAppBar
import com.hantash.weather_app.utils.formatDate
import com.hantash.weather_app.utils.formatDateDayOnly
import com.hantash.weather_app.utils.formatDecimals
import com.hantash.weather_app.utils.formatTime
import com.hantash.weather_app.utils.generateImageUrl
import com.hantash.weather_app.viewmodel.WeatherViewModel

@Composable
fun MainScreen(navController: NavController) {
    val viewModel = hiltViewModel<WeatherViewModel>()

    val weatherData = produceState<ResultAPI<WeatherResponse, Boolean, Exception>>(
        initialValue = ResultAPI(isLoading = true)
    ) {
        value = viewModel.fetchCurrentWeather("Istanbul")
    }.value

    if (weatherData.isLoading) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        ScreenContent(navController, weatherData.data)
    }
}

//@Preview
@Composable
private fun ScreenContent(
    navController: NavController? = null,
    weatherResponse: WeatherResponse? = null
) {
    val weeklyWeatherList = weatherResponse?.list
    val weather = weeklyWeatherList?.first()
    val todayWeather = weather?.weather?.first()
    val title = "${weatherResponse?.city?.name}, ${weatherResponse?.city?.country}"

    Scaffold(
        topBar = {
            BaseAppBar(
                title = title,
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = formatDate(weatherResponse?.list?.first()?.dt ?: 0),
                    style = MaterialTheme.typography.titleMedium
                )

                Surface(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(200.dp),
                    shape = CircleShape,
                    color = Color(0xFFFFC400)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        AppImage(url = generateImageUrl(todayWeather?.icon))
                        Text(
                            text = "${formatDecimals(weatherResponse?.list?.first()?.temp?.day ?: 0.0)}°",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = todayWeather?.main.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AppTextIcon(text = "${weather?.humidity}%", resourceId = R.drawable.ic_humidity)
                    AppTextIcon(
                        text = "${weather?.pressure} psi",
                        resourceId = R.drawable.ic_pressure
                    )
                    AppTextIcon(text = "${weather?.humidity}mph", resourceId = R.drawable.ic_wind)
                }
                HorizontalDivider()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AppTextIcon(
                        text = formatTime(weather?.sunrise ?: 0),
                        resourceId = R.drawable.ic_sunrise
                    )
                    AppTextIcon(
                        text = formatTime(weather?.sunset ?: 0),
                        resourceId = R.drawable.ic_sunset
                    )
                }

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(items = weeklyWeatherList ?: emptyList()) { weather ->
                        WeatherItem(weather)
                    }
                }
            }
        }
    )
}

@Preview
@Composable
private fun WeatherItem(weather: Item0? = null) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatDateDayOnly(weather?.dt ?: 0),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
            AppImage(url = generateImageUrl(weather?.weather?.first()?.icon))
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFFFC400)
            ) {
                Text(
                    modifier = Modifier
                        .background(Color(0xFFFFC400))
                        .padding(vertical = 6.dp, horizontal = 10.dp),
                    text = "${weather?.weather?.first()?.description}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Italic
                    )
                )
            }
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue.copy(alpha = 0.7f),
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append("${formatDecimals(weather?.temp?.max ?: 0.0)}°")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.Gray,
                    )
                ) {
                    append("${formatDecimals(weather?.temp?.min ?: 0.0)}°")
                }
            })
        }

        HorizontalDivider(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            thickness = 0.5.dp,
        )
    }
}
















