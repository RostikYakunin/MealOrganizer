package com.example.mealorganizer.data.profile.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "profile",
    indices = [Index(value = ["user_name"], unique = true)]
)
data class ProfileModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(defaultValue = "'NULL'")
    val name: String = "",

    @ColumnInfo(defaultValue = "'NULL'")
    val surname: String = "",

    @ColumnInfo(defaultValue = "0")
    val age: Byte = 0,

    @ColumnInfo(name = "email", defaultValue = "'NULL'")
    val email: String = "",

    @ColumnInfo(defaultValue = "0")
    val weight: Byte = 0,

    @ColumnInfo(name = "user_name", defaultValue = "'NULL'")
    val userName: String = "",

    @ColumnInfo(defaultValue = "'NULL'")
    val password: String = "",

    @ColumnInfo(name = "is_logged", defaultValue = "false")
    val isLoggedIn: Boolean = false,

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val image: ByteArray = ByteArray(0)
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProfileModel

        if (id != other.id) return false
        if (name != other.name) return false
        if (surname != other.surname) return false
        if (age != other.age) return false
        if (email != other.email) return false
        if (weight != other.weight) return false
        if (userName != other.userName) return false
        if (password != other.password) return false
        if (isLoggedIn != other.isLoggedIn) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + surname.hashCode()
        result = 31 * result + age
        result = 31 * result + email.hashCode()
        result = 31 * result + weight
        result = 31 * result + userName.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + isLoggedIn.hashCode()
        result = 31 * result + image.contentHashCode()
        return result
    }

}
