package com.example.rentifyx.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    private val firestore = FirebaseFirestore.getInstance()
    fun isUserLoggedIn(): Boolean = auth.currentUser != null

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    fun signOut() = auth.signOut()

    suspend fun signInWithGoogle(token: String): Boolean {
        try {
            val credential = GoogleAuthProvider.getCredential(token, null)
            val user = auth.signInWithCredential(credential).await()

            return user != null
        } catch (exception: Exception) {
            Log.d("Error occurred while registering user via Google", exception.toString())
            return false
        }
    }

    suspend fun saveUserDetails(userDetails: Map<String, String>): Boolean {
        return try {
            firestore.collection("users")
                .document(auth.currentUser?.uid!!)
                .set(userDetails)
                .await()

            true
        } catch (exception: Exception) {
            Log.d(
                "Error occurred while saving user details of signUp via Google",
                exception.toString()
            )
            false
        }
    }

    suspend fun checkIfUserHasAccount(): Boolean {
        return try {
            val document = firestore.collection("users").document(auth.currentUser!!.uid).get().await()
            return document.exists()
        } catch (exception: Exception) {
            Log.d(
                "Error occurred while fetch the user details of signUp via Google",
                exception.toString()
            )
            false
        }
    }
}