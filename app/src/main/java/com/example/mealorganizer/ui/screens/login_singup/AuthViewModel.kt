package com.example.mealorganizer.ui.screens.login_singup

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealorganizer.data.auth.FirebaseAuthRepository
import com.example.mealorganizer.data.profile.model.ProfileModel
import com.example.mealorganizer.data.profile.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "AuthViewModel"

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: FirebaseAuthRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _authenticationState =
        MutableStateFlow<AuthenticationState>(AuthenticationState.Idle)
    val authenticationState: StateFlow<AuthenticationState> = _authenticationState

    fun singUpWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            _authenticationState.value = AuthenticationState.Loading

            val authResult = authRepository.signUpWithEmailAndPassword(email, password)
            _authenticationState.value = if (authResult.isSuccess) {
                val user = authResult.getOrNull()?.user
                Log.i(TAG, "New user created: ${user?.email}")

                saveUserProfile(
                    ProfileModel(
                        uid = user?.uid.toString(),
                        email = user?.email.toString()
                    )
                )

                AuthenticationState.Success(user?.uid ?: "")
            } else {
                Log.e(
                    TAG,
                    "Something went wrong with signing up: ${authResult.exceptionOrNull()?.message}"
                )
                AuthenticationState.Error(authResult.exceptionOrNull()?.message ?: "")
            }


        }
    }

    fun logInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _authenticationState.value = AuthenticationState.Loading
            val result = authRepository.logInWithEmailAndPassword(email, password)

            _authenticationState.value = if (result.isSuccess) {
                Log.i(TAG, "User logged in: ${result.getOrNull()?.user?.email}")

                AuthenticationState.Success(result.getOrNull()?.user.toString())
            } else {
                Log.e(
                    TAG,
                    "Something went wrong with logging in: ${result.exceptionOrNull()?.message}"
                )
                AuthenticationState.Error(result.exceptionOrNull()?.message ?: "")
            }
        }
    }

    fun resetSignupState() {
        _authenticationState.value = AuthenticationState.Idle
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
                Log.i(TAG, "User profile saved locally.")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to save user profile: ${e.message}")
            }
        }
    }
}

sealed class AuthenticationState {
    data object Loading : AuthenticationState()
    data class Success(val userId: String) : AuthenticationState()
    data class Error(val errorMessage: String) : AuthenticationState()
    data object Idle : AuthenticationState()

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