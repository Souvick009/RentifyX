package com.example.rentifyx.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.rentifyx.repository.AuthRepository
import com.example.rentifyx.states.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.core.content.edit
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.example.rentifyx.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
    applicationContext: Application
) : AndroidViewModel(applicationContext) {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val pref = applicationContext.getSharedPreferences("config", Context.MODE_PRIVATE)
    private val isGuest = pref.getBoolean("guest", false)

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
        } else if (authRepository.isUserLoggedIn()) {
            AuthState.Authenticated(authRepository.getCurrentUser()!!)
        } else {
            AuthState.Unauthenticated
        }
    }

    fun markAsGuest(flag: Boolean) {
        pref.edit() { putBoolean("guest", flag) }
    }

    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(applicationContext.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(applicationContext, gso)

    fun signInWithGoogle(account: GoogleSignInAccount, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            account.idToken?.let { token ->
                val success = authRepository.signInWithGoogle(token)
                if (success) {
                    _authState.value = AuthState.Authenticated(authRepository.getCurrentUser()!!)
                }
                isLoading.value = false
                onResult(success)
            }
            account.email?.let {
                emailID = it
            }
            account.displayName?.let {
                displayName = it
            }
        }
    }
}