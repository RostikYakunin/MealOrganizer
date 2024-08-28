package com.example.mealorganizer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.mealorganizer.ui.navigation.HomeNavigation
import com.example.mealorganizer.ui.theme.MealOrganizerTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val TAG = "MainActivity"

@AndroidEntryPoint
class MealOrganizerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MealOrganizerTheme {
                HomeNavigation(navController = rememberNavController())
            }
        }
    }
}
