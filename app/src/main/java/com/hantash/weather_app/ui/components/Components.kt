package com.hantash.weather_app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hantash.weather_app.ui.navigation.EnumScreen

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BaseAppBar(
    title: String = "Country Name",
    enumScreen: EnumScreen = EnumScreen.MAIN_SCREEN,
    onActionButtonClicked: () -> Unit = {},
    onBackButtonClicked: () -> Unit = {}
) {
    TopAppBar(
        modifier = Modifier
            .shadow(elevation = 8.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        title = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(modifier = Modifier.align(Alignment.Center), text = title)
            }
        },
        actions = {
            if (enumScreen == EnumScreen.MAIN_SCREEN) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        modifier = Modifier.padding(4.dp),
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "Search Icon"
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = { onBackButtonClicked.invoke() }) {
                if (enumScreen != EnumScreen.MAIN_SCREEN) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Search Icon"
                    )
                }
            }
        }
    )
}