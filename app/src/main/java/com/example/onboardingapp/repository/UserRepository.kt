package com.example.onboardingapp.repository

import androidx.lifecycle.LiveData
import com.example.onboardingapp.model.UserDao
import com.example.onboardingapp.model.UserData
import com.example.onboardingapp.utils.security.EncryptionUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val userDao: UserDao,
    private val encryptionUtils: EncryptionUtils,
) {

    val allUsers: LiveData<List<UserData>> = userDao.getAllUsers()

    suspend fun insert(userData: UserData): Long {
        val encryptedPin = encryptionUtils.encrypt(userData.pin ?: "")
        val encryptedUserData = userData.copy(pin = encryptedPin)
        return withContext(Dispatchers.IO) {
            userDao.insert(encryptedUserData)
        }
    }

    suspend fun update(userData: UserData) {
        val encryptedPin = encryptionUtils.encrypt(userData.pin ?: "")
        val encryptedUserData = userData.copy(pin = encryptedPin)
        withContext(Dispatchers.IO) {
            userDao.update(encryptedUserData)
        }
    }

    suspend fun delete(userData: UserData) {
        withContext(Dispatchers.IO) {
            userDao.delete(userData)
        }
    }

    suspend fun getMostRecentUser(): UserData? {
        return withContext(Dispatchers.IO) {
            userDao.getMostRecentUser()?.let {
                val decryptedPin = encryptionUtils.decrypt(it.pin ?: "")
                it.copy(pin = decryptedPin)
            }
        }
    }
}