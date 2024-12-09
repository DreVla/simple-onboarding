package com.example.onboardingapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.onboardingapp.model.UserData
import com.example.onboardingapp.model.UserDatabase
import com.example.onboardingapp.repository.UserRepository
import com.example.onboardingapp.utils.security.EncryptionUtils
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    private val allUsers: LiveData<List<UserData>>

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        val encryptionUtils = EncryptionUtils(application)
        repository = UserRepository(userDao, encryptionUtils)
        allUsers = repository.allUsers
    }

    fun insert(userData: UserData) = viewModelScope.launch {
        repository.insert(userData)
    }

    fun update(userData: UserData) = viewModelScope.launch {
        repository.update(userData)
    }

    fun delete(userData: UserData) = viewModelScope.launch {
        repository.delete(userData)
    }

    fun getMostRecentUser(): LiveData<UserData?> = liveData {
        emit(repository.getMostRecentUser())
    }
}