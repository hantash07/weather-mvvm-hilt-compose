package com.hantash.weather_app.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hantash.weather_app.model.Favorite
import com.hantash.weather_app.ui.components.BaseAppBar
import com.hantash.weather_app.ui.navigation.EnumScreen
import com.hantash.weather_app.utils.Constant
import com.hantash.weather_app.utils.showToast
import com.hantash.weather_app.viewmodel.FavoriteViewmodel

@Composable
fun FavoriteScreen(navController: NavController) {
    ScreenContent(navController)
}

@Preview(showBackground = true)
@Composable
private fun ScreenContent(navController: NavController? = null) {
    Scaffold(
        topBar = {
            BaseAppBar(
                title = "Favorite Cities",
                enumScreen = EnumScreen.FAVORITE_SCREEN,
                onBackButtonClicked = {
                    navController?.popBackStack()
                }
            )
        },
        content = { padding ->
            val context = LocalContext.current
            val viewModel = hiltViewModel<FavoriteViewmodel>()
            viewModel.fetchFavorites()
            val favoriteList = viewModel.favoriteList.collectAsState().value

            ShowFavorites(
                Modifier.padding(padding), favoriteList,
                onShowWeather = { favorite ->
                    navController?.currentBackStackEntry?.savedStateHandle?.set(Constant.KEY_COUNTRY, favorite.city)
                    navController?.navigate(EnumScreen.MAIN_SCREEN.name) {
                        popUpTo(EnumScreen.FAVORITE_SCREEN.name) {inclusive = true}
                    }
                },
                onRemove = { favorite ->
                    viewModel.removeFavorite(favorite)
                    showToast(context, "City Removed From Favorite")
                }
            )
        }
    )
}

@Composable
private fun ShowFavorites(modifier: Modifier, favoriteList: List<Favorite>, onShowWeather: (Favorite) -> Unit, onRemove: (Favorite) -> Unit) {
    if (favoriteList.isNotEmpty()) {
        LazyColumn(modifier = modifier) {
            items(items = favoriteList) { favorite ->
                ItemFavorite(favorite, onShowWeather, onRemove)
            }
        }
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "No City Added As Favorite",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic,
                )
            )
        }
    }
}

@Composable
fun ItemFavorite(favorite: Favorite, onShowWeather: (Favorite) -> Unit, onRemove: (Favorite) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .shadow(elevation = 8.dp),
        onClick = { onShowWeather(favorite) },
        shape = RoundedCornerShape(corner = CornerSize(8.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "${favorite.city}, ${favorite.country}",
                style = MaterialTheme.typography.titleMedium
            )
            IconButton(
                onClick = {onRemove(favorite)}
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Icon",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
























