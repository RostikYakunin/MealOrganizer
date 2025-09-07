package com.example.mealorganizer.data.profile.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "profile",
    indices = [Index(value = ["email"], unique = true)]
)
data class ProfileModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(defaultValue = "'NULL'")
    val uid: String? = "",

    @ColumnInfo(defaultValue = "'NULL'")
    val name: String? = "",

    @ColumnInfo(defaultValue = "'NULL'")
    val surname: String? = "",

    @ColumnInfo(name = "user_name", defaultValue = "'NULL'")
    val userName: String? = "",

    @ColumnInfo(defaultValue = "0")
    val age: Byte? = 0,

    @ColumnInfo(name = "email", defaultValue = "'NULL'")
    val email: String? = "",

    @ColumnInfo(defaultValue = "0")
    val weight: Byte? = 0,

    @ColumnInfo(defaultValue = "")
    val image: String? = ""
)
