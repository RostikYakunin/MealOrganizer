package com.example.mealorganizer.di.modules

import com.example.mealorganizer.data.MealOrganizerRoomDataBase
import com.example.mealorganizer.data.profile.repository.ProfileDao
import com.example.mealorganizer.data.profile.repository.ProfileRepository
import com.example.mealorganizer.data.profile.repository.ProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideProfileDao(database: MealOrganizerRoomDataBase): ProfileDao {
        return database.profileDao()
    }

    @Provides
    @Singleton
    fun provideProfileRepository(dao: ProfileDao): ProfileRepository {
        return ProfileRepositoryImpl(dao)
    }
}