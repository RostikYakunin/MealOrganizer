package com.example.mealorganizer.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mealorganizer.data.profile.model.ProfileModel
import com.example.mealorganizer.data.profile.repository.ProfileDao

@Database(entities = [ProfileModel::class], version = 4)
abstract class MealOrganizerRoomDataBase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
}