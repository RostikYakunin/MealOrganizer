package com.example.mealorganizer.ui.screens.login_singup

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mealorganizer.R
import com.example.mealorganizer.ui.navigation.NavigationRoutesEnum

@Composable
fun GreetingScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            AppLogo(modifier)

            AppNameText(modifier)

            AppDescriptionText(modifier)

            Spacer(modifier = Modifier.padding(vertical = 30.dp))

            SignUpButton(
                modifier = modifier,
                onClick = { navController.navigate(NavigationRoutesEnum.SIGN_UP_SCREEN.name) },
                enabled = true
            )

            LogInButton(
                modifier = modifier,
                onClick = { navController.navigate(NavigationRoutesEnum.LOG_IN_SCREEN.name) },
                enabled = true
            )
        }
    }
}

@Composable
fun LogInButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    buttonColor: Color = MaterialTheme.colorScheme.primaryContainer,
    textColor: Color = MaterialTheme.colorScheme.primary
) {
    ActionButtonModel(
        modifier = modifier,
        buttonColor = buttonColor,
        onClick = onClick,
        text = stringResource(R.string.log_in),
        textColor = textColor,
        enabled = enabled
    )
}

@Composable
fun SignUpButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean
) {
    ActionButtonModel(
        modifier = modifier,
        buttonColor = MaterialTheme.colorScheme.primary,
        onClick = onClick,
        text = stringResource(R.string.sign_up),
        textColor = MaterialTheme.colorScheme.onPrimary,
        enabled = enabled
    )
}

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.mipmap.ic_launcher_adaptive_fore3),
        contentDescription = "App icon",
        alignment = Alignment.Center,
        contentScale = ContentScale.FillBounds,
        modifier = modifier
            .width(250.dp)
            .height(250.dp)
    )
}

@Composable
private fun AppNameText(modifier: Modifier = Modifier) {
    Text(
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        text = stringResource(R.string.app_title),
        modifier = modifier
            .offset(y = (-25).dp)
    )
}

@Composable
private fun AppDescriptionText(modifier: Modifier) {
    Text(
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        text = stringResource(R.string.app_description),
        textAlign = TextAlign.Center,
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.medium_padding))
    )
}

@Composable
fun ActionButtonModel(
    modifier: Modifier = Modifier,
    buttonColor: Color,
    onClick: () -> Unit,
    text: String,
    enabled: Boolean = false,
    textColor: Color
) {
    Button(
        shape = RoundedCornerShape(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        ),
        modifier = modifier
            .width(250.dp)
            .padding(top = dimensionResource(R.dimen.big_padding))
            .border(width = 1.dp, color = MaterialTheme.colorScheme.primary),
        onClick = onClick,
        enabled = enabled
    ) {
        Text(
            style = MaterialTheme.typography.titleSmall,
            color = textColor,
            text = text
        )
    }
}

@Composable
@Preview
private fun GreetingScreenPreview() {
    GreetingScreen(navController = rememberNavController())
}