package com.example.mealorganizer.ui.screens.home.leftbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mealorganizer.R
import com.example.mealorganizer.ui.navigation.NavigationRoutesEnum

@Composable
fun LeftMealBar(modifier: Modifier = Modifier, navController: NavHostController) {
    ModalDrawerSheet(
        drawerShape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 30.dp,
            bottomEnd = 30.dp,
            bottomStart = 0.dp
        ),
        drawerContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        modifier = modifier
            .padding(
                horizontal = 10.dp,
                vertical = 25.dp
            )
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .fillMaxSize()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .weight(1F)
                    .background(Color.White)
                    .clickable {
                        navController.navigate(NavigationRoutesEnum.PROFILE_INFO_SCREEN.name)
                    }
            ) {
                Image(
                    painter = painterResource(R.drawable.green),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )

                LeftMealBarItem(
                    modifier = modifier,
                    text = "Name Surname"
                )
            }

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .weight(3F)
                    .fillMaxHeight()
            ) {
                LeftMealBarItem(
                    modifier = modifier,
                    text = "Profile",
                    imageVector = Icons.Default.AccountBox
                )

                LeftMealBarItem(
                    modifier = modifier,
                    text = "Calendar",
                    imageVector = Icons.Default.CalendarMonth
                )

                LeftMealBarItem(
                    modifier = modifier,
                    text = "Barcode scanner",
                    imageVector = Icons.Default.DocumentScanner
                )

                LeftMealBarItem(
                    modifier = modifier,
                    text = "Calculator",
                    imageVector = Icons.Default.Calculate
                )

                LeftMealBarItem(
                    modifier = modifier,
                    text = "Preferences",
                    imageVector = Icons.Default.Settings
                )

                LeftMealBarItem(
                    modifier = modifier,
                    text = "Notification",
                    imageVector = Icons.Default.Notifications
                )

                LeftMealBarItem(
                    modifier = modifier,
                    text = "Help",
                    imageVector = Icons.AutoMirrored.Filled.Help
                )
            }
        }
    }
}

@Composable
private fun LeftMealBarItem(
    modifier: Modifier = Modifier,
    text: String = "Default text",
    imageVector: ImageVector = Icons.Default.Image,

    ) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        modifier = modifier
            .sizeIn(
                minWidth = 300.dp,
                minHeight = 60.dp,
                maxHeight = 100.dp,
                maxWidth = 300.dp
            )
            .padding(
                vertical = 12.dp
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            Image(
                imageVector = imageVector,
                contentDescription = "profile`s photo",
                alignment = Alignment.Center,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                modifier = modifier
                    .weight(1F)
                    .size(50.dp)
                    .clip(CircleShape)
            )

            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = modifier
                    .weight(3F)
                    .padding(start = 20.dp)
            )
        }
    }
}

@Preview
@Composable
fun LeftBarPreview() {
    LeftMealBar(navController = rememberNavController())
}

@Preview
@Composable
fun ProfileCardPreview() {
    LeftMealBarItem()
}