package com.example.mealorganizer.ui.screens.login_singup

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealorganizer.domain.auth.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "AuthViewModel"

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _signupState = MutableStateFlow<SignupState>(SignupState.Idle)
    val signupState: StateFlow<SignupState> = _signupState

    fun singUpWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            _signupState.value = SignupState.Loading
            val result = authRepository.signUpWithEmailAndPassword(email, password)
            _signupState.value = if (result.isSuccess) {
                SignupState.Success(result.getOrNull()?.user?.uid ?: "")
            } else {
                SignupState.Error(result.exceptionOrNull()?.message ?: "Unknown error, please try again later..")

            }
        }
    }

    fun resetSignupState() {
        _signupState.value = SignupState.Idle
    }
}

sealed class SignupState {
    data object Loading : SignupState()
    data class Success(val userId: String) : SignupState()
    data class Error(val errorMessage: String) : SignupState()
    data object Idle : SignupState()

    fun showToast(
        context: Context,
        text: String,
    ) {
        val toast = Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        )
        toast.setGravity(Gravity.TOP, 0, 100)
        toast.show()
    }
}