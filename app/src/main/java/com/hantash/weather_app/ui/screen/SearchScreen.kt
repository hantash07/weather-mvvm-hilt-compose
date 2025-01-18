package com.hantash.weather_app.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hantash.weather_app.model.Favorite
import com.hantash.weather_app.ui.components.AppButton
import com.hantash.weather_app.ui.components.AppSearchField
import com.hantash.weather_app.ui.components.BaseAppBar
import com.hantash.weather_app.ui.navigation.EnumScreen
import com.hantash.weather_app.utils.Constant
import com.hantash.weather_app.utils.debug
import com.hantash.weather_app.utils.showToast
import com.hantash.weather_app.viewmodel.FavoriteViewmodel

@Composable
fun SearchScreen(navController: NavController) {
    ScreenContent(navController)
}

@Preview
@Composable
private fun ScreenContent(navController: NavController? = null) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BaseAppBar(
                title = "Search Country",
                enumScreen = EnumScreen.SEARCH_SCREEN,
                onBackButtonClicked = {
                    navController?.popBackStack()
                }
            )
        },

        content = { paddingValues ->
            Surface(modifier = Modifier.padding(paddingValues)) {
                SearchCountry { searchValue ->
                    debug(message = "Search Result = $searchValue")

                    navController?.previousBackStackEntry?.savedStateHandle?.set(
                        Constant.KEY_COUNTRY,
                        searchValue
                    )
                    navController?.popBackStack()
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SearchCountry(searchValue: (String) -> Unit = {}) {
    val context = LocalContext.current
    // This state will keep the value after orientation
    val searchState = rememberSaveable {
        mutableStateOf("")
    }
    val validateSearchState = remember(searchState.value) {
        searchState.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    Card(
        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(corner = CornerSize(4.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.End
        ) {
            AppSearchField(
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp),
                label = "Country Name",
                value = searchState.value,
                onValueChange = { value ->
                    if (value.all { char -> char.isLetter() || char.isWhitespace() })
                        searchState.value = value
                },
                keyboardActions = KeyboardActions {
                    if (!validateSearchState) return@KeyboardActions
                    keyboardController?.hide()
                }
            )
            AppButton(
                modifier =  Modifier.padding(end = 16.dp, bottom = 16.dp),
                title = "Search"
            ) {
                if (!validateSearchState) {
                    showToast(context, "Country can't be empty.")
                    return@AppButton
                }

                searchValue(searchState.value)
                searchState.value = ""
                keyboardController?.hide()
            }
        }
    }
}















