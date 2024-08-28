@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mealorganizer.ui.screens.login_singup

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mealorganizer.R
import com.example.mealorganizer.ui.navigation.NavigationRoutesEnum

@Composable
fun SignUpScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel()
) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = modifier.fillMaxSize()
    ) {
        var username by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val isUserInfoValid = username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
        val signupState by viewModel.signupState.collectAsState()
        val context = LocalContext.current

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            AppLogo()

            SignUpTextField(
                value = username,
                onValueChange = { username = it },
                label = "Username"
            )

            SignUpTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email"
            )

            SignUpTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                isPassword = true
            )

            SignUpButton(
                onClick = {
                    viewModel.singUpWithEmailAndPassword(email, password)
                },
                enabled = isUserInfoValid,
                modifier = modifier
            )

            showToastWithSignUpResult(signupState, context, viewModel, navController)

            HorizontalDividerRow()

            // TODO: implement logic with Google Sign Up and others
        }
    }
}

private fun showToastWithSignUpResult(
    signupState: SignupState,
    context: Context,
    viewModel: AuthViewModel,
    navController: NavHostController
) {
    when (signupState) {
        is SignupState.Loading -> {
            signupState.showToast(context,"Signing up...")
        }

        is SignupState.Success -> {
            signupState.showToast(context,"Signing up successful")
            viewModel.resetSignupState()
            navController.navigate(NavigationRoutesEnum.HOME_SCREEN.name)
        }

        is SignupState.Error -> {
            val errorMessage = signupState.errorMessage
            signupState.showToast(context, errorMessage)
            viewModel.resetSignupState()
        }

        is SignupState.Idle -> {}
    }
}

@Composable
private fun HorizontalDividerRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(
                vertical = dimensionResource(id = R.dimen.big_padding)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = dimensionResource(R.dimen.big_padding))
        )

        Text(
            text = "or",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = dimensionResource(R.dimen.big_padding))
        )
    }
}

@Composable
fun SignUpTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                text = label,
            )
        },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFF2F5FA),
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .background(Color.Transparent)
            .padding(vertical = dimensionResource(R.dimen.small_padding))
    )
}

@Composable
@Preview
private fun SignInPreview() {
    SignUpScreen(navController = rememberNavController())
}
