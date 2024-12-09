package com.example.onboardingapp.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAllUsers(): LiveData<List<UserData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userData: UserData): Long

    @Update
    suspend fun update(userData: UserData)

    @Delete
    suspend fun delete(userData: UserData)

    @Query("SELECT * FROM user ORDER BY uid DESC LIMIT 1")
    suspend fun getMostRecentUser(): UserData?
}