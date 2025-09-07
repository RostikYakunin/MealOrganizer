package com.example.mealorganizer.data.firebase

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "FirebaseStorageRepo"
private const val IMAGES_PATH = "images/profiles/"

class FirebaseStorageRepository @Inject constructor(
    private val firebaseStorage: FirebaseStorage,
) {
    private val storageRef = firebaseStorage.reference

    suspend fun saveImage(uri: Uri, profileUid: String): Result<String> {
        return try {
            Log.d(TAG, "Starting image saving to Firebase Storage")

            val path = storageRef.child("$IMAGES_PATH${profileUid}.jpg")
            val uploadTask = path.putFile(uri).await()
            val downloadUrl = path.downloadUrl.await().toString()

            Log.d(TAG, "User's photo successfully saved with URL: $downloadUrl")
            Result.success(downloadUrl)
        } catch (e: Exception) {
            Log.e(TAG, "Error saving photo: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun fetchImageByUid(uid: String): Result<Bitmap> {
        return try {
            Log.d(TAG, "Starting image fetching from Firebase Storage")
            val imageBytes = storageRef.child("$IMAGES_PATH$uid.jpg").getBytes(Long.MAX_VALUE).await()
            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            Log.d(TAG, "User's photo successfully fetched")
            Result.success(bitmap)
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching photo: ${e.message}")
            Result.failure(e)
        }
    }
}