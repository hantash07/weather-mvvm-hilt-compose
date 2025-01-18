package com.hantash.weather_app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.hantash.weather_app.R
import com.hantash.weather_app.ui.navigation.EnumScreen
import com.hantash.weather_app.utils.capsFirstLetter
import com.hantash.weather_app.utils.showToast

enum class EnumAppBarAction {
    SEARCH,
    FAVORITE,
    ABOUT,
    SETTINGS
}

enum class EnumAction {
    ADD,
    REMOVE
}

enum class EnumUnit(val unit: String) {
    FAHRENHEIT("°F"),
    CELSIUS("°C");
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BaseAppBar(
    title: String = "Country Name",
    enumScreen: EnumScreen = EnumScreen.MAIN_SCREEN,
    isFavorite: Boolean = false,
    onActionButtonClicked: (EnumAppBarAction) -> Unit = {},
    onAddRemoveFavorite: (EnumAction) -> Unit = {},
    onBackButtonClicked: () -> Unit = {}
) {
    val appBarMenuState = remember {
        mutableStateOf(false)
    }

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
                IconButton(onClick = { onActionButtonClicked.invoke(EnumAppBarAction.SEARCH) }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                }
                IconButton(onClick = { appBarMenuState.value = !appBarMenuState.value }) {
                    Icon(
                        modifier = Modifier.padding(4.dp),
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "Search Icon"
                    )
                }
                DropdownMenu(
                    modifier = Modifier.background(color = Color.White),
                    expanded = appBarMenuState.value,
                    onDismissRequest = { appBarMenuState.value = false }
                ) {
                    AppDropDownMenuItem("Favorites", Icons.Default.Favorite) {
                        appBarMenuState.value = false
                        onActionButtonClicked(EnumAppBarAction.FAVORITE)
                    }
                    AppDropDownMenuItem("About", Icons.Default.Info) {
                        appBarMenuState.value = false
                        onActionButtonClicked(EnumAppBarAction.ABOUT)
                    }
                    AppDropDownMenuItem("Settings", Icons.Default.Settings) {
                        appBarMenuState.value = false
                        onActionButtonClicked(EnumAppBarAction.SETTINGS)
                    }
                }
            }
        },
        navigationIcon = {
            if (enumScreen == EnumScreen.MAIN_SCREEN) {
                IconButton(onClick = {
                    val action = if (isFavorite) EnumAction.REMOVE else EnumAction.ADD
                    onAddRemoveFavorite.invoke(action)
                }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite Icon",
                        tint = Color.Red.copy(.7f)
                    )
                }
            } else {
                IconButton(onClick = { onBackButtonClicked.invoke() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back Icon"
                    )
                }
            }
        }
    )
}

@Composable
fun AppDropDownMenuItem(text: String, icon: ImageVector, onClickItem: () -> Unit) {
    DropdownMenuItem(
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "Menu Icon"
            )
        },
        text = { Text(text) },
        onClick = {
            onClickItem.invoke()
        }
    )
}


@Composable
fun AppImage(url: String) {
    Image(
        modifier = Modifier.size(80.dp),
        painter = rememberAsyncImagePainter(
            model = url
        ),
        contentDescription = "Image",
        contentScale = ContentScale.Crop
    )
}

@Preview
@Composable
fun AppTextIcon(text: String = "Preview", resourceId: Int = R.drawable.ic_humidity) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(resourceId),
            contentDescription = "Icon Small",
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, style = MaterialTheme.typography.titleSmall)
    }
}

@Preview(showBackground = true)
@Composable
fun AppSearchField(
    modifier: Modifier = Modifier,
    value: String = "",
    label: String = "",
    onValueChange: (String) -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text,
    imeActions: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = Color.Black
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeActions,
        ),
        keyboardActions = keyboardActions,
    )
}

@Preview(showBackground = true)
@Composable
fun AppRadioButton(
    enumUnit: EnumUnit = EnumUnit.CELSIUS,
    isSelected: Boolean = false,
    onClickRadioBtn: (EnumUnit) -> Unit = {}
) {
    Row(
        modifier = Modifier.clickable {
            onClickRadioBtn.invoke(enumUnit)
        },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            onClick = {onClickRadioBtn.invoke(enumUnit)},
            selected = isSelected
        )
        Text(
            modifier = Modifier.padding(end = 16.dp),
            text = "${enumUnit.name.capsFirstLetter()} ${enumUnit.unit}",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    title: String = "Search",
    onClickBtn: () -> Unit = {}
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(corner = CornerSize(4.dp)),
        onClick = {onClickBtn.invoke()}
    ) {
        Text(title, style = MaterialTheme.typography.titleMedium)
    }
}


















