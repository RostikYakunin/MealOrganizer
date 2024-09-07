package com.example.mealorganizer.ui.screens.login_singup

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mealorganizer.R
import com.example.mealorganizer.ui.navigation.NavigationRoutesEnum

@Composable
fun LogInScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel()
) {

    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = modifier.fillMaxSize()
    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val isUserInfoValid = email.isNotEmpty() && password.isNotEmpty()
        val logInState by viewModel.authUiState.collectAsState(AuthUiState.Idle)
        val context = LocalContext.current

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            AppLogo()

            SignUpInTextField(
                value = email,
                onValueChange = { email = it },
                label = stringResource(R.string.email)
            )

            SignUpInTextField(
                value = password,
                onValueChange = { password = it },
                label = stringResource(R.string.password),
                isPassword = true
            )

            LogInButton(
                onClick = { viewModel.logInWithEmail(email, password) },
                enabled = isUserInfoValid,
                buttonColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.onPrimary,
                modifier = modifier
            )

            if (logInState != AuthUiState.Idle) {
                LogInToastResult(
                    authUiState = logInState,
                    context = context,
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
    }
}

@Composable
private fun LogInToastResult(
    authUiState: AuthUiState,
    context: Context,
    viewModel: AuthViewModel,
    navController: NavHostController
) {
    when (authUiState) {
        is AuthUiState.Loading -> {
            authUiState.showToast(context, stringResource(R.string.logging_in))
        }

        is AuthUiState.Success -> {
            authUiState.showToast(context, stringResource(R.string.logging_successful))
            viewModel.resetSignupState()
            navController.navigate(NavigationRoutesEnum.HOME_SCREEN.name)
        }

        is AuthUiState.Error -> {
            val errorMessage = authUiState.errorMessage
            authUiState.showToast(context, errorMessage)
            viewModel.resetSignupState()
        }

        is AuthUiState.Idle -> {}
    }
}

@Composable
@Preview
fun LoginPreview() {
    LogInScreen(navController = rememberNavController())
}