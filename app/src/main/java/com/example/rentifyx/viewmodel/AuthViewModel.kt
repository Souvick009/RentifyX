package com.example.rentifyx.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentifyx.R
import com.example.rentifyx.repository.AuthRepository
import com.example.rentifyx.states.AuthState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
    applicationContext: Application
) : AndroidViewModel(applicationContext) {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val pref = applicationContext.getSharedPreferences("config", Context.MODE_PRIVATE)
    private var isGuest = pref.getBoolean("guest", false)
    var hasCompletedProfileAndSignedIn = pref.getBoolean("signedIn", false)
        private set

    var emailID = ""
        private set
    var displayName = ""
        private set

    val isLoading = mutableStateOf(false)

    init {
        checkLoginStatus()
    }

    fun checkLoginStatus() {
        _authState.value = if (isGuest) {
            AuthState.Guest
        } else if (authRepository.isUserLoggedIn() && hasCompletedProfileAndSignedIn) {
            AuthState.Authenticated(authRepository.getCurrentUser()!!)
        } else {
            AuthState.Unauthenticated
        }
    }

    fun markAsGuest(flag: Boolean) {
        isGuest = flag
        _authState.value = AuthState.Guest
        pref.edit() { putBoolean("guest", flag) }
    }

    fun markAsCompletedProfileAndSignedIn(flag: Boolean) {
        hasCompletedProfileAndSignedIn = flag
        _authState.value = AuthState.Authenticated(authRepository.getCurrentUser()!!)
        pref.edit() { putBoolean("signedIn", flag) }
    }

    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(applicationContext.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(applicationContext, gso)

    fun signInWithGoogle(account: GoogleSignInAccount, onResult: (String) -> Unit) {
        viewModelScope.launch {
            account.idToken?.let { token ->
                val signedInWithGoogle = authRepository.signInWithGoogle(token)
                val hasAccount = authRepository.checkIfUserHasAccount()
                markAsCompletedProfileAndSignedIn(true)
                isLoading.value = false
                if (signedInWithGoogle) {
                    if (hasAccount) {
                        onResult("hasAccount")
                    } else {
                        onResult("doesntHasAccount")
                    }
                } else {
                    onResult("error")
                }
            }
            account.email?.let {
                emailID = it
            }
            account.displayName?.let {
                displayName = it
            }
        }
    }

    fun saveUserDetails(name: String, phoneNumber: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val taskStatus = authRepository.saveUserDetails(
                mapOf(
                    "userName" to name,
                    "phoneNumber" to phoneNumber,
                    "emailId" to emailID
                )
            )
            isLoading.value = false
            onResult(taskStatus)
        }
    }
}