package com.example.onboardingapp.repository

import com.example.onboardingapp.model.UserDao
import com.example.onboardingapp.model.UserData
import com.example.onboardingapp.utils.security.EncryptionUtils
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class UserRepositoryTest {

    private lateinit var userDao: UserDao
    private lateinit var encryptionUtils: EncryptionUtils
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        userDao = mock(UserDao::class.java) // Mocking UserDao
        encryptionUtils = mock(EncryptionUtils::class.java) // Mocking EncryptionUtils
        userRepository = UserRepository(userDao, encryptionUtils)
    }

    @Test
    fun `GIVEN a user with a pin WHEN inserting the user THEN the pin is encrypted and inserted into the database`() = runBlocking {
        val userData = UserData(
            uid = 1,
            firstName = "First",
            lastName = "Last",
            email = "john.doe@example.com",
            phoneNumber = "1234567890",
            password = "password",
            pin = "1234",
        )
        val encryptedPin = "encrypted1234"
        val encryptedUserData = userData.copy(pin = encryptedPin)
        `when`(encryptionUtils.encrypt("1234")).thenReturn(encryptedPin)
        `when`(userDao.insert(encryptedUserData)).thenReturn(1L)

        val result = userRepository.insert(userData)

        verify(encryptionUtils).encrypt("1234") // Verify encryption is called
        verify(userDao).insert(encryptedUserData) // Verify UserDao.insert is called
        assertEquals(1L, result)
    }

    @Test
    fun `GIVEN an encrypted user in the database WHEN fetching the most recent user THEN the pin is decrypted`() = runBlocking {
        val encryptedUser = UserData(
            uid = 1,
            firstName = "First",
            lastName = "Last",
            email = "john.doe@example.com",
            phoneNumber = "1234567890",
            password = "password",
            pin = "encrypted1234",
        )
        val decryptedPin = "1234"
        `when`(userDao.getMostRecentUser()).thenReturn(encryptedUser)
        `when`(encryptionUtils.decrypt(anyString())).thenReturn(decryptedPin) // Match any string

        val result = userRepository.getMostRecentUser()

        verify(userDao).getMostRecentUser() // Verify DAO is queried
        verify(encryptionUtils).decrypt("encrypted1234") // Ensure correct decryption call
        assertEquals("1234", result?.pin)
    }

    @Test
    fun `GIVEN a user with a pin WHEN updating the user THEN the pin is encrypted and updated in the database`() = runBlocking {
        val userData = UserData(
            uid = 1,
            firstName = "First",
            lastName = "Last",
            email = "john.doe@example.com",
            phoneNumber = "1234567890",
            password = "password",
            pin = "1234",
        )
        val encryptedPin = "encrypted1234"
        val encryptedUserData = userData.copy(pin = encryptedPin)
        `when`(encryptionUtils.encrypt("1234")).thenReturn(encryptedPin)

        userRepository.update(userData)

        verify(encryptionUtils).encrypt("1234") // Verify encryption is called
        verify(userDao).update(encryptedUserData) // Verify UserDao.update is called
    }

    @Test
    fun `GIVEN a user WHEN deleting the user THEN the user is removed from the database`() = runBlocking {
        val userData = UserData(
            uid = 1,
            firstName = "First",
            lastName = "Last",
            email = "john.doe@example.com",
            phoneNumber = "1234567890",
            password = "password",
            pin = "1234",
        )

        userRepository.delete(userData)

        verify(userDao).delete(userData) // Verify UserDao.delete is called
    }
}
