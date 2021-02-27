package com.englishcentral.githubusers

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.englishcentral.githubusers.database.AppDatabase
import com.englishcentral.githubusers.database.GithubUserDao
import com.englishcentral.githubusers.database.GithubUserDetailsEntity
import com.englishcentral.githubusers.database.GithubUserEntity
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class RoomDatabaseTest {

    private var userDao: GithubUserDao? = null


    @Before
    fun setup() {
        AppDatabase.TEST_MODE = true
        userDao = AppDatabase.getInstance(InstrumentationRegistry.getInstrumentation().context)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun testInsertUserDetailsToRoomDatabase() {
        val githubUserEntity = GithubUserDetailsEntity(1000, "bestAwesomeName","www.google.com","John Wick", "haha, please", 22, 400)
        userDao?.insertAll(githubUserEntity)

        val retrievedUser = userDao?.getGithubUserDetails(githubUserEntity.id)

        Assert.assertEquals(githubUserEntity.name, retrievedUser?.name)
    }

    @Test
    fun testInsertUserToRoomDatabase() {
        val githubUserEntity = GithubUserEntity(466, "dragon 22", "www.facebook.com")
        userDao?.insertAll(githubUserEntity)

        val retrievedUser = userDao?.getGithubUser(githubUserEntity.id)

        Assert.assertEquals(githubUserEntity.login, retrievedUser?.login)
    }
}