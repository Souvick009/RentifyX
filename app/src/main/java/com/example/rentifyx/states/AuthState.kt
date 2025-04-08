package com.example.rentifyx.states

import com.google.firebase.auth.FirebaseUser

sealed class AuthState {
    data class Authenticated(val firebaseUser: FirebaseUser) : AuthState()
    data object Guest: AuthState()
    data object Unauthenticated: AuthState()
}