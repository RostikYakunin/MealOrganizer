@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mealorganizer.ui.screens.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mealorganizer.R
import com.example.mealorganizer.data.profile.model.ProfileModel
import com.example.mealorganizer.ui.navigation.NavigationRoutesEnum
import com.example.mealorganizer.ui.screens.login_singup.ActionButtonModel
import com.example.mealorganizer.ui.screens.login_singup.AuthViewModel

private const val TAG = "ProfileScreen"

@Composable
fun ProfileInfoScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val uid = authViewModel.getCurrentUser()?.uid
    val profile by profileViewModel.profile.collectAsState(initial = ProfileModel())

    val defaultBitmap = if (profile.image.isNotEmpty())
        BitmapFactory.decodeByteArray(profile.image, 0, profile.image.size) else {
        Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888).apply {
            eraseColor(Color.GRAY)
        }
    }

    LaunchedEffect(uid) {
        uid?.let {
            profileViewModel.fetchProfileByUid(it)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopProfileInfoBar(
            onBackClick = { navController.popBackStack() },
            modifier = modifier
        )

        ProfileImage(modifier, defaultBitmap)

        ProfileInfoLines(modifier, profile)

        EditProfileButton(
            onClick = { }, //TODO: create edit profile screen
            modifier = modifier
        )

        LogOutButton(
            onClick = {
                authViewModel.logOut()
                navController.navigate(NavigationRoutesEnum.GREETING_SCREEN.name)
            },
            modifier = modifier
        )
    }
}

@Composable
private fun EditProfileButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ActionButtonModel(
        buttonColor = MaterialTheme.colorScheme.primaryContainer,
        onClick = onClick,
        text = stringResource(R.string.edit_profile),
        textColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = modifier
    )
}

@Composable
private fun ProfileImage(
    modifier: Modifier,
    defaultBitmap: Bitmap
) {
    Box(
        modifier = modifier
            .padding(vertical = dimensionResource(R.dimen.big_padding))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clip(CircleShape)
    ) {
        Image(
            bitmap = defaultBitmap.asImageBitmap(),
            contentDescription = "Profile`s image",
            alignment = Alignment.Center,
            modifier = modifier
                .sizeIn(
                    minHeight = 150.dp,
                    minWidth = 150.dp,
                    maxHeight = 300.dp,
                    maxWidth = 300.dp
                )
        )
    }
}

@Composable
private fun TopProfileInfoBar(onBackClick: () -> Unit, modifier: Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.profile_info),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        modifier = modifier
            .padding(top = 10.dp, start = 5.dp)
    )
}

@Composable
private fun ProfileInfoLines(
    modifier: Modifier,
    profile: ProfileModel
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End,
        modifier = modifier
    ) {
        InfoLine(
            label = stringResource(R.string.profile_name),
            value = profile.name,
            modifier
        )
        InfoLine(
            label = stringResource(R.string.profile_surname),
            value = profile.surname,
            modifier
        )
        InfoLine(
            label = stringResource(R.string.username),
            value = profile.userName,
            modifier
        )
        InfoLine(
            label = stringResource(R.string.profile_age),
            value = profile.age,
            modifier
        )
        InfoLine(
            label = stringResource(R.string.profile_weight),
            value = profile.weight,
            modifier
        )
        InfoLine(
            label = stringResource(R.string.email),
            value = profile.email,
            modifier
        )
    }
}

@Composable
fun LogOutButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ActionButtonModel(
        modifier = modifier,
        buttonColor = MaterialTheme.colorScheme.primary,
        onClick = onClick,
        text = stringResource(R.string.log_out),
        textColor = MaterialTheme.colorScheme.onPrimary,
        enabled = true
    )
}

@Composable
fun <T> InfoLine(
    label: String,
    value: T,
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = value.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        HorizontalDivider(
            modifier = modifier
                .padding(horizontal = 20.dp)
        )
    }

}

@Composable
@Preview
fun ProfileScreenPreview() {
    ProfileInfoScreen(navController = rememberNavController())
}

@Composable
@Preview
fun InfoLinePreview() {
    InfoLine(label = "Name", "Ivan")
}