package com.example.mealorganizer.data.data_store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.mealorganizer.data.data_store.ProfilePreferencesKeys.UID_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = "user_preferences")

class ProfilePreferencesRepository @Inject constructor(
    private val context: Context
) {
    fun getUserUID(): Flow<String> {
        return context.dataStore.data
            .catch {
                if (it is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }
            .map {
                it[UID_KEY] ?: ""
            }
    }

    suspend fun saveUserUID(uid: String) {
        context.dataStore.edit {
            it[UID_KEY] = uid
        }
    }

    suspend fun clearUserUid() {
        context.dataStore.edit { preferences ->
            preferences.remove(UID_KEY)
        }
    }

    suspend fun isValueNotPresent(key: String): Boolean {
        val preferences = context.dataStore.data.first()
        return preferences[UID_KEY] == null
    }
}

object ProfilePreferencesKeys{
    val UID_KEY = stringPreferencesKey("user_uid")
}
