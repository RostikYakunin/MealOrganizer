@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mealorganizer.ui.screens.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mealorganizer.data.profile.model.ProfileModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onBackClick: () -> Unit = { navController.popBackStack() },
    profile: ProfileModel = ProfileModel()
) {
    val defaultBitmap = if (profile.image.isEmpty()) {
        Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
            .apply {
                eraseColor(Color.GRAY)
            }
    } else {
        BitmapFactory.decodeByteArray(profile.image, 0, profile.image.size)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Profile info",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            modifier = modifier
                .padding(top = 10.dp, start = 5.dp)

        )

        Box(
            modifier = modifier
                .padding(vertical = 50.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .clip(CircleShape)
        ) {
            Image(
                bitmap = defaultBitmap.asImageBitmap(),
                contentDescription = "Profile`s image",
                alignment = Alignment.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .sizeIn(
                        minHeight = 150.dp,
                        minWidth = 150.dp,
                        maxHeight = 300.dp,
                        maxWidth = 300.dp
                    )
            )
        }

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End,
            modifier = modifier

        ) {
            InfoLine(label = "Name", value = profile.name, modifier)
            InfoLine(label = "Surname", value = profile.surname, modifier)
            InfoLine(label = "Username", value = profile.userName, modifier)
            InfoLine(label = "Age", value = profile.age, modifier)
            InfoLine(label = "Weight", value = profile.weight, modifier)
            InfoLine(label = "Email", value = profile.email, modifier)
        }
    }
}

@Composable
fun <T> InfoLine(
    label: String,
    value: T,
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = value.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        HorizontalDivider(
            modifier = modifier
                .padding(horizontal = 20.dp)
        )
    }

}

@Composable
@Preview
fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}

@Composable
@Preview
fun InfoLinePreview() {
    InfoLine(label = "Name", "Ivan")
}