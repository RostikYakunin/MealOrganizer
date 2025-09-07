package com.example.mealorganizer.ui.screens.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun fetchPhotoFromGallery(
    profileViewModel: ProfileViewModel,
    uid: String?
) = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickVisualMedia()
) { uri ->
    uri?.let {
        profileViewModel.updateProfileImageUri(it, uid)
    }
}