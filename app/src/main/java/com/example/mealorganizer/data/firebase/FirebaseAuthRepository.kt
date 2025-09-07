package com.example.mealorganizer.data.firebase

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "FirebaseAuthRepository"

class FirebaseAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun signUpWithEmailAndPassword(email: String, password: String): Result<AuthResult> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Log.i(TAG, "New user successfully signed up")
            Result.success(result)
        } catch (e: Exception) {
            Log.e(TAG, "Error signing up")
            Result.failure(e)
        }
    }

    suspend fun logInWithEmailAndPassword(email: String, password: String): Result<AuthResult> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Log.i(TAG, "User successfully logged in")
            Result.success(result)
        } catch (e: Exception) {
            Log.e(TAG, "Error logging in")
            Result.failure(e)
        }
    }

    fun getCurrentUser() = firebaseAuth.currentUser

    fun signOut() {
        firebaseAuth.signOut()
    }
}