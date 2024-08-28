package com.example.mealorganizer.data.profile.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mealorganizer.data.profile.model.ProfileModel

@Dao
interface ProfileDao {
    @Insert
    suspend fun save(profile: ProfileModel)

    @Update
    suspend fun update(profile: ProfileModel)

    @Delete
    suspend fun delete(profile: ProfileModel)

    @Query("SELECT * FROM profile WHERE id = :id")
    suspend fun findByUserName(id: Long): ProfileModel
}