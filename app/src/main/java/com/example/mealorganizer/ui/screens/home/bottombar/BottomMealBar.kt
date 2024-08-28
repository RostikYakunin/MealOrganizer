package com.example.mealorganizer.ui.screens.home.bottombar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun BottomMealBar(modifier: Modifier = Modifier) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        modifier = modifier
            .fillMaxWidth()

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .sizeIn(
                    maxHeight = 100.dp,
                )
        ) {
            BottomMealBarItem(
                imageVector = Icons.Default.Bookmarks,
                text = "Favorites",
            )
            BottomMealBarItem(
                imageVector = Icons.Default.Search,
                text = "Search",
            )
            BottomMealBarItem(
                imageVector = Icons.Default.History,
                text = "History",
            )
        }
    }
}

@Composable
fun BottomMealBarItem(
    imageVector: ImageVector,
    text: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(0.dp),
        modifier = modifier
            .padding(horizontal = 20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = "Navigation button",
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Text(
                text = text,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}