package com.example.rentifyx.screens

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.rentifyx.reusablecomposable.BaseScreen
import com.example.rentifyx.reusablecomposable.CustomInputField
import com.example.rentifyx.reusablecomposable.PrimaryButton
import com.example.rentifyx.viewmodel.AuthViewModel

@Composable
fun UserDetailsScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    var fullName by rememberSaveable { mutableStateOf(authViewModel.displayName) }
    var fullNameError by remember { mutableStateOf<String?>(null) }

    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var phoneError by remember { mutableStateOf<String?>(null) }

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
                    fullName = it
                    if (fullNameError != null) fullNameError = null
                },
                label = "Full Name",
                isError = fullNameError != null,
                errorMessage = fullNameError,
                leadingIcon = {
                    Icon(Icons.Outlined.AccountCircle, contentDescription = null)
                }
            )

            Spacer(modifier = Modifier.height(18.dp))

            CustomInputField(
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it
                    if (phoneError != null) phoneError = null
                },
                label = "Phone Number",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                isError = phoneError != null,
                errorMessage = phoneError,
                leadingIcon = {
                    Icon(Icons.Outlined.Phone, contentDescription = null)
                }
            )
//            CustomInputField(
//                value = phoneNumber,
//                onValueChange = {
//                    phoneNumber = it
//                    if (phoneError != null) phoneError = null
//                },
//                label = "Phone Number",
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
//                isError = phoneError != null,
//                errorMessage = phoneError,
//                leadingIcon = {
//                    Icon(Icons.Outlined.Phone, contentDescription = null)
//                }
//            )
//
//            CustomInputField(
//                value = phoneNumber,
//                onValueChange = {
//                    phoneNumber = it
//                    if (phoneError != null) phoneError = null
//                },
//                label = "Phone Number",
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
//                isError = phoneError != null,
//                errorMessage = phoneError,
//                leadingIcon = {
//                    Icon(Icons.Outlined.Phone, contentDescription = null)
//                }
//            )



            Spacer(modifier = Modifier.height(18.dp))

            PrimaryButton(
                onClick = {

                },
                text = "Continue"
            )
        }
    }
}

