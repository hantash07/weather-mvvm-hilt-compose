package com.hantash.weather_app.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hantash.weather_app.model.Favorite
import com.hantash.weather_app.ui.components.AppSearchField
import com.hantash.weather_app.ui.components.BaseAppBar
import com.hantash.weather_app.ui.navigation.EnumScreen
import com.hantash.weather_app.utils.Constant
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
            val viewModel = hiltViewModel<FavoriteViewmodel>()

            SearchCountry(Modifier.padding(paddingValues)) { searchValue ->
                Log.d(Constant.APP_DEBUG, "Search Result = $searchValue")
                viewModel.addFavorite(Favorite(cityName = searchValue))

                navController?.currentBackStackEntry?.savedStateHandle?.set(Constant.KEY_COUNTRY, searchValue)
                navController?.navigate(EnumScreen.MAIN_SCREEN.name)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SearchCountry(modifier: Modifier = Modifier, searchValue: (String) -> Unit = {}) {
    // This state will keep the value after orientation
    val searchState = rememberSaveable {
        mutableStateOf("")
    }
    val validateSearchState = remember(searchState.value) {
        searchState.value.trim().isNotEmpty()
    }
    val keyboardController  = LocalSoftwareKeyboardController.current

    AppSearchField(
        modifier = modifier,
        label = "Country",
        value = searchState.value,
        onValueChange = { value ->
            if (value.all { char -> char.isLetter() || char.isWhitespace() })
                searchState.value = value
        },
        keyboardActions = KeyboardActions {
            if (!validateSearchState) return@KeyboardActions

            searchValue(searchState.value)
            searchState.value = ""

            keyboardController?.hide()
        }

    )
}















