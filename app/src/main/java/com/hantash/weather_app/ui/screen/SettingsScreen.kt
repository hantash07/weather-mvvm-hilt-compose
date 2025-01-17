package com.hantash.weather_app.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hantash.weather_app.ui.components.AppRadioButton
import com.hantash.weather_app.ui.components.BaseAppBar
import com.hantash.weather_app.ui.components.EnumUnit
import com.hantash.weather_app.ui.navigation.EnumScreen
import com.hantash.weather_app.utils.debug
import com.hantash.weather_app.viewmodel.SettingsViewmodel

@Composable
fun SettingsScreen(navController: NavController, viewmodel: SettingsViewmodel) {
    ScreenContent(navController, viewmodel)
}

@Composable
private fun ScreenContent(navController: NavController, viewmodel: SettingsViewmodel) {
    val radioOptionState = remember {
        mutableStateOf(EnumUnit.CELSIUS)
    }

    val savedUnit: EnumUnit = viewmodel.getTempUnit().collectAsState(EnumUnit.CELSIUS).value
    radioOptionState.value = savedUnit
    debug("Saved Unit: ${savedUnit.name}")

    Scaffold (
        topBar = {
            BaseAppBar(
                title = "Settings",
                enumScreen = EnumScreen.SETTINGS_SCREEN,
                onBackButtonClicked = {
                    navController.popBackStack()
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Temperature Units",
                    style = MaterialTheme.typography.titleMedium
                )
                AppRadioButton(
                    enumUnit = EnumUnit.CELSIUS,
                    radioOptionState.value == EnumUnit.CELSIUS,
                    onClickRadioBtn = {
                        radioOptionState.value = EnumUnit.CELSIUS
                        viewmodel.changeTempUnit(EnumUnit.CELSIUS)
                    }
                )
                AppRadioButton(
                    enumUnit = EnumUnit.FAHRENHEIT,
                    radioOptionState.value == EnumUnit.FAHRENHEIT,
                    onClickRadioBtn = {
                        radioOptionState.value = EnumUnit.FAHRENHEIT
                        viewmodel.changeTempUnit(EnumUnit.FAHRENHEIT)
                    }
                )
            }
        }
    )
}



















