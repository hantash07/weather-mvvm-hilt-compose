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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.painterResource
import com.hantash.weather_app.R
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



















