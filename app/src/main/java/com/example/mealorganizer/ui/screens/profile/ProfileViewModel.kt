package com.example.mealorganizer.ui.screens.profile

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealorganizer.data.data_store.ProfilePreferencesRepository
import com.example.mealorganizer.data.firebase.FirebaseStorageRepository
import com.example.mealorganizer.data.profile.model.ProfileModel
import com.example.mealorganizer.data.profile.repository.ProfileRepository
import com.google.firebase.storage.FileDownloadTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

private const val TAG = "ProfileViewModel"

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val profilePreferencesRepository: ProfilePreferencesRepository,
    private val firebaseStorageRepository: FirebaseStorageRepository
) : ViewModel() {

    private val _profile = MutableStateFlow(ProfileModel())
    val profile: StateFlow<ProfileModel> = _profile.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    val profileUid = profilePreferencesRepository.getUserUID()

    fun updateProfileImageUri(uri: Uri, uid: String?) {
        Log.i(TAG, "Starting updating profile's image")
        viewModelScope.launch {
            uid?.let {
                val result = firebaseStorageRepository.saveImage(uri, it)
                result.getOrNull()?.let { downloadUrl ->
                    Log.d(TAG, "Saving image URL to database")
                    val updatedProfile = _profile.value.copy(image = downloadUrl)
                    profileRepository.update(updatedProfile)
                    _profile.value = updatedProfile
                } ?: run {
                    _error.value = result.exceptionOrNull()?.message ?: "Failed to upload image"
                }
            } ?: run {
                _error.value = "User ID is null"
            }
        }
    }

    fun updateProfile(profile: ProfileModel) {
        viewModelScope.launch {
            Log.d(TAG, "Updating profile: $profile")
            profileRepository.update(profile)
            _profile.value = profile
        }
    }

    fun fetchProfileByUid(uid: String) {
        Log.i(TAG, "Starting fetching info with uid = $uid")
        viewModelScope.launch {
            val result = profileRepository.findByCloudUid(uid)
            _profile.value = result
        }
    }
}