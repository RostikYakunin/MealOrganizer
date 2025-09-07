package com.example.mealorganizer.ui.screens.login_singup

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealorganizer.data.data_store.ProfilePreferencesKeys
import com.example.mealorganizer.data.data_store.ProfilePreferencesRepository
import com.example.mealorganizer.data.firebase.FirebaseAuthRepository
import com.example.mealorganizer.data.profile.model.ProfileModel
import com.example.mealorganizer.data.profile.repository.ProfileRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "AuthViewModel"

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: FirebaseAuthRepository,
    private val profileRepository: ProfileRepository,
    private val profilePreferencesRepository: ProfilePreferencesRepository
) : ViewModel() {

    private val _authUiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val authUiState: StateFlow<AuthUiState> = _authUiState

    fun singUpWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            _authUiState.value = AuthUiState.Loading

            val authResult = authRepository.signUpWithEmailAndPassword(email, password)
            _authUiState.value = if (authResult.isSuccess) {
                val user = authResult.getOrNull()?.user
                Log.i(TAG, "New user created: ${user?.email}")

                val profileModel = ProfileModel(
                    uid = user?.uid.toString(),
                    email = user?.email.toString()
                )

                saveUserProfile(profileModel)
                saveUidIfNotPresent(user)

                AuthUiState.Success(user?.uid ?: "")
            } else {
                Log.e(
                    TAG,
                    "Something went wrong with signing up: ${authResult.exceptionOrNull()?.message}"
                )
                AuthUiState.Error(authResult.exceptionOrNull()?.message ?: "")
            }
        }
    }

    fun logInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _authUiState.value = AuthUiState.Loading
            val authResult = authRepository.logInWithEmailAndPassword(email, password)

            _authUiState.value = if (authResult.isSuccess) {
                val user = authResult.getOrNull()?.user
                Log.i(TAG, "User logged in: ${authResult.getOrNull()?.user?.email}")

                saveUidIfNotPresent(user)
                AuthUiState.Success(authResult.getOrNull()?.user.toString())
            } else {
                Log.e(
                    TAG,
                    "Something went wrong with logging in: ${authResult.exceptionOrNull()?.message}"
                )
                AuthUiState.Error(authResult.exceptionOrNull()?.message ?: "")
            }
        }
    }

    fun resetSignupState() {
        _authUiState.value = AuthUiState.Idle
    }

    fun getCurrentUser() = authRepository.getCurrentUser()

    fun logOut() {
        val currentUser = getCurrentUser()
        Log.i(TAG, "User with email = ${currentUser?.email}")
        authRepository.signOut()
    }

    private fun saveUserProfile(profile: ProfileModel) {
        viewModelScope.launch {
            try {
                profileRepository.save(profile)
                Log.i(TAG, "User profile saved to local DB.")
            } catch (e: Exception) {
                Log.e(TAG, "Failed saving user`s profile to local DB: ${e.message}")
            }
        }
    }

    private suspend fun saveUidIfNotPresent(user: FirebaseUser?) {
        if (profilePreferencesRepository.isValueNotPresent(ProfilePreferencesKeys.UID_KEY.name)) {
            Log.i(TAG, "Uid is not present, starting saving... ")
            profilePreferencesRepository.saveUserUID(user?.uid.toString())
        }
    }
}

sealed class AuthUiState {
    data object Loading : AuthUiState()
    data class Success(val userId: String) : AuthUiState()
    data class Error(val errorMessage: String) : AuthUiState()
    data object Idle : AuthUiState()

    fun showToast(
        context: Context,
        text: String,
    ) {
        val toast = Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        )
        toast.show()
    }
}