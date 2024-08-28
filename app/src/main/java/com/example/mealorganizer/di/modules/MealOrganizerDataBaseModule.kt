package com.example.mealorganizer.di.modules

import android.content.Context
import androidx.room.Room
import com.example.mealorganizer.data.MealOrganizerRoomDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MealOrganizerDataBaseModule {

    @Provides
    @Singleton
    fun provideMealOrganizerDataBase(context: Context): MealOrganizerRoomDataBase {
        return Room.databaseBuilder(
            context = context.applicationContext,
            MealOrganizerRoomDataBase::class.java,
            "meal_organizer_db"
        )
            .build()
    }
}