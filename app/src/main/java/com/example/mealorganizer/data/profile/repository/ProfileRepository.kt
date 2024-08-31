package com.example.mealorganizer.data.profile.repository

import com.example.mealorganizer.data.profile.model.ProfileModel

interface ProfileRepository {
    suspend fun save(profile: ProfileModel)
    suspend fun update(profile: ProfileModel)
    suspend fun delete(profile: ProfileModel)
    suspend fun findByLocalId(id: Long): ProfileModel
    suspend fun findByCloudUid(uid: String): ProfileModel
}