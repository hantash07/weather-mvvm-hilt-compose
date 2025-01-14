package com.hantash.weather_app.ui.screen

import android.content.Context
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.hantash.weather_app.model.City
import com.hantash.weather_app.model.Favorite
import com.hantash.weather_app.model.Item0
import com.hantash.weather_app.model.WeatherResponse
import com.hantash.weather_app.ui.components.AppImage
import com.hantash.weather_app.ui.components.AppTextIcon
import com.hantash.weather_app.ui.components.BaseAppBar
import com.hantash.weather_app.ui.components.EnumAction
import com.hantash.weather_app.ui.components.EnumAppBarAction
import com.hantash.weather_app.ui.navigation.EnumScreen
import com.hantash.weather_app.utils.EnumDateFormat
import com.hantash.weather_app.utils.debug
import com.hantash.weather_app.utils.formatDateTime
import com.hantash.weather_app.utils.formatDecimals
import com.hantash.weather_app.utils.generateImageUrl
import com.hantash.weather_app.utils.showToast
import com.hantash.weather_app.viewmodel.FavoriteViewmodel
import com.hantash.weather_app.viewmodel.WeatherViewModel

@Composable
fun MainScreen(navController: NavController, countryName: String? = null) {
    val viewModel = hiltViewModel<WeatherViewModel>()

    val weatherData = produceState<ResultAPI<WeatherResponse, Boolean, Exception>>(
        initialValue = ResultAPI(isLoading = true)
    ) {
        value = viewModel.fetchCurrentWeather(countryName ?: "Istanbul")
    }.value

    if (weatherData.isLoading && weatherData.data != null) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        ScreenContent(navController, weatherData.data)
    }
}

@Preview
@Composable
private fun ScreenContent(
    navController: NavController? = null,
    weatherResponse: WeatherResponse? = null
) {
    val city = weatherResponse?.city
    val weeklyWeatherList = weatherResponse?.list
    val weather = weeklyWeatherList?.first()
    val todayWeather = weather?.weather?.first()
    val title = "${weatherResponse?.city?.name}, ${weatherResponse?.city?.country}"

    val context = LocalContext.current
    val viewModel = hiltViewModel<FavoriteViewmodel>()
    viewModel.isFavorite(city = city?.name ?: "")

    Scaffold(
        topBar = {
            BaseAppBar(
                title = title,
                isFavorite = viewModel.isFavoriteState.collectAsState().value,
                onActionButtonClicked = { enumAction ->
                    navigateTo(navController, enumAction)
                },
                onAddRemoveFavorite = { action ->
                    debug("isFavorite Action: ${action.name}")
                    addRemoveFavorite(context, viewModel, action, city)
                }
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
                    text = formatDateTime(weatherResponse?.list?.first()?.dt?.toLong(), EnumDateFormat.EEE_D_MMM),
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
                        text = formatDateTime(weather?.sunrise?.toLong(), EnumDateFormat.HH_MM_A),
                        resourceId = R.drawable.ic_sunrise
                    )
                    AppTextIcon(
                        text = formatDateTime(weather?.sunset?.toLong(), EnumDateFormat.HH_MM_A),
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

@Composable
private fun WeatherItem(weather: Item0) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatDateTime(weather.dt.toLong(), EnumDateFormat.EEE),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
            AppImage(url = generateImageUrl(weather.weather.first().icon))
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFFFC400)
            ) {
                Text(
                    modifier = Modifier
                        .background(Color(0xFFFFC400))
                        .padding(vertical = 6.dp, horizontal = 10.dp),
                    text = weather.weather.first().description,
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
                    append("${formatDecimals(weather.temp.max)}°")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.Gray,
                    )
                ) {
                    append("${formatDecimals(weather.temp.min)}°")
                }
            })
        }

        HorizontalDivider(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            thickness = 0.5.dp,
        )
    }
}

private fun navigateTo(navController: NavController?, enumAction: EnumAppBarAction) {
    val screenName: String = when(enumAction) {
        EnumAppBarAction.SEARCH -> EnumScreen.SEARCH_SCREEN.name
        EnumAppBarAction.FAVORITE -> EnumScreen.FAVORITE_SCREEN.name
        EnumAppBarAction.ABOUT -> EnumScreen.ABOUT_SCREEN.name
        EnumAppBarAction.SETTINGS -> EnumScreen.SETTINGS_SCREEN.name
    }
    navController?.navigate(screenName)
}

private fun addRemoveFavorite(context: Context, viewModel: FavoriteViewmodel, action: EnumAction, city: City?) {
    when(action) {
        EnumAction.ADD -> {
            viewModel.addFavorite(Favorite(city = city?.name ?: "", country = city?.country ?: ""))
            showToast(context, "City Added Into Favorite")
        }
        EnumAction.REMOVE -> {
            viewModel.removeFavorite(Favorite(city = city?.name ?: "", country = city?.country ?: ""))
            showToast(context, "City Removed From Favorite")
        }
    }
}













