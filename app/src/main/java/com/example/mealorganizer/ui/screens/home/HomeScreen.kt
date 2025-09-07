package com.example.mealorganizer.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mealorganizer.ui.screens.home.bottombar.BottomMealBar
import com.example.mealorganizer.ui.screens.home.leftbar.LeftNavigationBar
import com.example.mealorganizer.ui.screens.home.topbar.TopMealBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            LeftNavigationBar(
                modifier = modifier,
                navController = navController
            )
        },
        modifier = modifier
            .fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                TopMealBar(
                    coroutineScope = coroutineScope,
                    drawerState = drawerState,
                    modifier = modifier
                )
            },
            bottomBar = { BottomMealBar(modifier = modifier) }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {

            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}



