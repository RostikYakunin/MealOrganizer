package com.example.mealorganizer.ui

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.mealorganizer.ui.navigation.HomeNavigation
import com.example.mealorganizer.ui.navigation.NavigationRoutesEnum
import com.example.mealorganizer.ui.theme.MealOrganizerTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val TAG = "MainActivity"

@AndroidEntryPoint
class MealOrganizerActivity : ComponentActivity() {
    @Inject
    lateinit var auth: FirebaseAuth
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            }
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(permission)
            }
        }

        setContent {
            MealOrganizerTheme {
                val currentUser = auth.currentUser
                val startDestination = if (currentUser != null) {
                    NavigationRoutesEnum.HOME_SCREEN.name
                } else {
                    NavigationRoutesEnum.GREETING_SCREEN.name
                }

                HomeNavigation(
                    navController = rememberNavController(),
                    startRoute = startDestination
                )
            }
        }
    }
}
