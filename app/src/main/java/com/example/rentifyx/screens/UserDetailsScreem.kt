package com.example.rentifyx.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.rentifyx.navigation.Routes
import com.example.rentifyx.reusablecomposable.BaseScreen
import com.example.rentifyx.reusablecomposable.CustomInputField
import com.example.rentifyx.reusablecomposable.PrimaryButton
import com.example.rentifyx.viewmodel.AuthViewModel

@Composable
fun UserDetailsScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    var fullName by rememberSaveable { mutableStateOf(authViewModel.displayName) }
    var fullNameError = remember { mutableStateOf<String?>(null) }

    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var phoneNumberError = remember { mutableStateOf<String?>(null) }

    BaseScreen(
        modifier = Modifier,
        isAppBarNeeded = false,
        dividerColor = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 15.dp)
                .verticalScroll(rememberScrollState())
                .imePadding(), // push everything above keyboard
        ) {
            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "Complete Your Profile",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "We just need a few more details to personalize your experience.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(18.dp))

            CustomInputField(
                value = fullName,
                onValueChange = {
                    fullName = it.trim()
                    isNameValid(fullName, fullNameError)
                },
                label = "Full Name",
                isError = fullNameError.value != null,
                errorMessage = fullNameError.value,
                leadingIcon = {
                    Icon(Icons.Outlined.AccountCircle, contentDescription = null)
                }
            )

            Spacer(modifier = Modifier.height(18.dp))

            CustomInputField(
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it.trim()
                    isNumberValid(phoneNumber, phoneNumberError)
                    Log.d("check", phoneNumberError.value.toString())
                },
                label = "Phone Number",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                isError = phoneNumberError.value != null,
                errorMessage = phoneNumberError.value,
                leadingIcon = {
                    Icon(Icons.Outlined.Phone, contentDescription = null)
                }
            )

            Spacer(modifier = Modifier.height(18.dp))

            PrimaryButton(
                onClick = {
                    if (isInputValid(fullName, phoneNumber, fullNameError, phoneNumberError)) {
                        authViewModel.isLoading.value = true
                        authViewModel.saveUserDetails(fullName, phoneNumber, onResult = { success ->
                            if (success) {
                                authViewModel.markAsCompletedProfileAndSignedIn(true)
                                navController.navigate(Routes.HomeScreen.route) {
                                    popUpTo(Routes.WelcomeScreen.route) { inclusive = true }
                                }
                            } else {
                                authViewModel.isLoading.value = false
                                Toast.makeText(
                                    context,
                                    "Could not save the details, please try again",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        })
                    }
                },
                enabled = !authViewModel.isLoading.value,
                composables = {
                    if (authViewModel.isLoading.value)
                        CircularProgressIndicator()
                    else
                        Text("Continue", style = MaterialTheme.typography.bodyLarge)
                }
            )
        }
    }
}

private fun isInputValid(
    name: String,
    number: String,
    nameError: MutableState<String?>,
    numberError: MutableState<String?>
): Boolean {
    return isNameValid(name, nameError) && isNumberValid(number, numberError)
}

private fun isNameValid(name: String, nameError: MutableState<String?>): Boolean {
    Log.d("check", name)
    return if (name.isNotEmpty()) {
        nameError.value = null
        true
    } else {
        nameError.value = "Name is a mandatory field"
        false
    }
}

private fun isNumberValid(number: String, numberError: MutableState<String?>): Boolean {
    if (number.isNotEmpty()) {
        if (number.matches(Regex("^\\+?[0-9]+$"))) {
            numberError.value = null
            return true
        } else {
            numberError.value = "Number is not in a valid format"
            return false
        }
    } else {
        numberError.value = null
        return true
    }
}

