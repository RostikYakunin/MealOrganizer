package com.example.mealorganizer.di.modules

import android.content.Context
import com.example.mealorganizer.data.data_store.ProfilePreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Singleton
    @Provides
    fun provideProfilePrefRepository(@ApplicationContext context: Context) : ProfilePreferencesRepository {
        return ProfilePreferencesRepository(context)
    }
}