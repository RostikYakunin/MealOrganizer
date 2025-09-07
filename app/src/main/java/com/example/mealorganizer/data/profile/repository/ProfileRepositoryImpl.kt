package com.example.mealorganizer.data.profile.repository

import com.example.mealorganizer.data.profile.model.ProfileModel
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val dao: ProfileDao
) : ProfileRepository {

    override suspend fun save(profile: ProfileModel) = dao.save(profile)

    override suspend fun update(profile: ProfileModel) = dao.update(profile)

    override suspend fun delete(profile: ProfileModel) = dao.delete(profile)

    override suspend fun findByLocalId(id: Long): ProfileModel {
        return dao.findByLocalId(id) ?: ProfileModel()
    }

    override suspend fun findByCloudUid(uid: String): ProfileModel {
        return dao.findByCloudUid(uid) ?: ProfileModel()
    }
}