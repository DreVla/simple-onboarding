package com.example.onboardingapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user", indices = [Index(value = ["email"], unique = true)])
data class UserData(

    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0,

    @ColumnInfo(name = "first_name")
    val firstName: String? = "",

    @ColumnInfo(name = "last_name")
    val lastName: String? = "",

    @ColumnInfo(name = "phone_number")
    val phoneNumber: String? = "",

    @ColumnInfo(name = "email")
    val email: String? = "",

    @ColumnInfo(name = "password")
    val password: String? = "",

    @ColumnInfo(name = "pin")
    val pin: String? = "",
)