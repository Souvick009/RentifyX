package com.example.rentifyx.repository

import android.util.Log
import androidx.browser.trusted.Token
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    fun isUserLoggedIn(): Boolean = auth.currentUser != null

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    fun signOut() = auth.signOut()

    suspend fun signInWithGoogle(token: String): Boolean {
        try {
            val credential = GoogleAuthProvider.getCredential(token, null)
            val user = auth.signInWithCredential(credential).await()

            return user != null
        } catch (exception: Exception) {
            Log.d("Error occured while registering user via Google", exception.toString())
            return false
        }
    }
}