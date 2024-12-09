package com.example.onboardingapp.model

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.onboardingapp.utils.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: UserDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        userDao = database.userDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun testInsertAndGetAllUsers() = runBlocking {
        val userData = UserData(
            uid = 1,
            firstName = "First",
            lastName = "Last",
            email = "john.doe@example.com",
            phoneNumber = "1234567890",
            password = "password",
            pin = "1234",
        )
        userDao.insert(userData)

        val users =
            userDao.getAllUsers().getOrAwaitValue()
        assertEquals(1, users.size)
        assertEquals(userData.firstName, users[0].firstName)
    }

    @Test
    fun testUpdateUser() = runBlocking {
        val userData = UserData(
            uid = 1,
            firstName = "First",
            lastName = "Last",
            email = "john.doe@example.com",
            phoneNumber = "1234567890",
            password = "password",
            pin = "1234",
        )
        userDao.insert(userData)

        val updatedUser = userData.copy(firstName = "New First")
        userDao.update(updatedUser)

        val mostRecentUser = userDao.getMostRecentUser()
        assertEquals("New First", mostRecentUser?.firstName)
    }

    @Test
    fun testDeleteUser() = runBlocking {
        val userData = UserData(
            uid = 1,
            firstName = "First",
            lastName = "Last",
            email = "john.doe@example.com",
            phoneNumber = "1234567890",
            password = "password",
            pin = "1234",
        )
        userDao.insert(userData)

        userDao.delete(userData)

        val mostRecentUser = userDao.getMostRecentUser()
        assertNull(mostRecentUser)
    }

    @Test
    fun testGetMostRecentUser() = runBlocking {
        val userData1 = UserData(
            uid = 1,
            firstName = "First1",
            lastName = "Last1",
            email = "email1@example.com",
            phoneNumber = "1234567890",
            password = "password",
            pin = "1234",
        )
        val userData2 = UserData(
            uid = 1,
            firstName = "First2",
            lastName = "Last2",
            email = "email2@example.com",
            phoneNumber = "1234567890",
            password = "password",
            pin = "1234",
        )
        userDao.insert(userData1)
        userDao.insert(userData2)

        val mostRecentUser = userDao.getMostRecentUser()
        assertEquals(userData2.firstName, mostRecentUser?.firstName)
    }
}
