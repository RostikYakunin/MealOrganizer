package com.example.mealorganizer.ui.screens.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealorganizer.data.profile.model.ProfileModel
import com.example.mealorganizer.data.profile.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ProfileViewModel"

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private var _profile = MutableStateFlow(ProfileModel())
    val profile = _profile.asStateFlow()

    private val _profileImageUri = MutableStateFlow<Uri?>(null)
    val profileImageUri = _profileImageUri.asStateFlow()

    fun updateProfileImageUri(uri: Uri) {

        _profileImageUri.value = uri
    }

    fun fetchProfileByUid(uid: String) {
        Log.i(TAG, "Starting fetching info with uid = $uid")
        viewModelScope.launch {
            val result = profileRepository.findByCloudUid(uid)
            _profile.value = result
        }
    }
}