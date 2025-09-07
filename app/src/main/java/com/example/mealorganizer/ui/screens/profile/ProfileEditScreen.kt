package com.example.mealorganizer.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mealorganizer.R
import com.example.mealorganizer.data.profile.model.ProfileModel
import com.example.mealorganizer.ui.screens.login_singup.ActionButtonModel
import com.example.mealorganizer.ui.screens.login_singup.AuthViewModel


@Composable
fun ProfileEditScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val uid = authViewModel.getCurrentUser()?.uid
    val profile by profileViewModel.profile.collectAsState(initial = ProfileModel())
    val error by profileViewModel.error.collectAsState()
    var name by remember { mutableStateOf(profile.name ?: "") }
    var surname by remember { mutableStateOf(profile.surname ?: "") }
    var username by remember { mutableStateOf(profile.userName ?: "") }
    var age by remember { mutableStateOf(profile.age?.toString() ?: "") }
    var weight by remember { mutableStateOf(profile.weight?.toString() ?: "") }
    var isLoading by remember { mutableStateOf(false) }

    val galleryLauncher = fetchPhotoFromGallery(profileViewModel, uid)

    LaunchedEffect(profile) {
        isLoading = false
    }

    LaunchedEffect(uid) {
        uid?.let {
            profileViewModel.fetchProfileByUid(it)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer),
    ) {
        TopProfileInfoBar(
            text = stringResource(R.string.profile_info_editing),
            onBackClick = { navController.popBackStack() },
            modifier = modifier
        )

        if (isLoading) {
            CircularProgressIndicator(modifier = modifier.padding(16.dp))
        } else {
            ProfileImage(
                modifier = modifier,
                profileImageUrl = profile.image,
                defaultPainter = painterResource(android.R.drawable.ic_menu_gallery),
                galleryLauncher = galleryLauncher
            )
        }

        if (error != null) {
            Text(
                text = error!!,
                color = MaterialTheme.colorScheme.error,
                modifier = modifier.padding(8.dp)
            )
        }

        EditLine(
            label = "Name",
            initialValue = name,
            placeholder = profile.name ?: "",
            onValueChange = { name = it }
        )

        EditLine(
            label = "Surname",
            initialValue = surname,
            placeholder = profile.surname ?: "",
            onValueChange = { surname = it }
        )

        EditLine(
            label = "Username",
            initialValue = username,
            placeholder = profile.userName ?: "",
            onValueChange = { username = it }
        )

        EditLine(
            label = "Age",
            initialValue = age,
            placeholder = profile.age?.toString() ?: "",
            onValueChange = { age = it }
        )

        EditLine(
            label = "Weight",
            initialValue = weight,
            placeholder = profile.weight?.toString() ?: "",
            onValueChange = { weight = it }
        )

        ActionButtonModel(
            buttonColor = MaterialTheme.colorScheme.primary,
            onClick = {
                val updatedProfile = profile.copy(
                    name = name,
                    surname = surname,
                    userName = username,
                    age = age.toByteOrNull() ?: profile.age,
                    weight = weight.toByteOrNull() ?: profile.weight,
                    uid = uid
                )
                profileViewModel.updateProfile(updatedProfile)
                navController.popBackStack()
            },
            text = "Save changes",
            textColor = MaterialTheme.colorScheme.onPrimary,
            modifier = modifier.padding(top = 12.dp),
            enabled = !isLoading
        )
    }
}

@Composable
fun EditLine(
    modifier: Modifier = Modifier,
    label: String,
    initialValue: String,
    placeholder: String = "",
    onValueChange: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var value by remember { mutableStateOf(initialValue) }
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End,
        modifier = modifier.padding(12.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = modifier
                    .weight(1F)
                    .padding(start = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            TextField(
                value = value,
                onValueChange = {
                    value = it
                    onValueChange(it)
                },
                placeholder = {
                    if (value.isEmpty() && !isFocused) {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                textAlign = TextAlign.End
                            ),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    textAlign = TextAlign.End
                ),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF2F5FA),
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                ),
                modifier = modifier
                    .weight(2.5F)
                    .padding(end = 8.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            if (value == initialValue) {
                                value = ""
                            }
                            isFocused = true
                        } else {
                            if (value.isEmpty()) {
                                value = initialValue
                            }
                            isFocused = false
                        }
                    }
            )
        }

        HorizontalDivider(
            modifier = modifier
                .padding(horizontal = 20.dp)
        )
    }
}

@Preview
@Composable
private fun ProfileEditPreview() {
    ProfileEditScreen(navController = rememberNavController())
}