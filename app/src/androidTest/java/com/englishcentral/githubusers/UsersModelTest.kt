package com.englishcentral.githubusers

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.englishcentral.githubusers.database.AppDatabase
import com.englishcentral.githubusers.model.MainActivityModel
import com.englishcentral.githubusers.model.UserDetailsActivityModel
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class UsersModelTest {
    var model: MainActivityModel? = null
    var userDetailsModel: UserDetailsActivityModel? = null
    @Before
    fun setup() {
        AppDatabase.TEST_MODE = true
        model = MainActivityModel(InstrumentationRegistry.getInstrumentation().context)
        userDetailsModel = UserDetailsActivityModel(InstrumentationRegistry.getInstrumentation().context)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun testFetchUsersAndStoringInRoomDatabase() {
        model?.getUsers { users ->
            for (user in users) {
                val id = user.id
                if  (id == null) {
                    Assert.fail()
                }else {
                    val retrievedUser = AppDatabase.getInstance(InstrumentationRegistry.getInstrumentation().context).getGithubUser(id)
                    Assert.assertNotNull(retrievedUser)
                    Assert.assertEquals(retrievedUser.avatar_url, user.avatar_url)
                }
            }
        }
    }

    @Test
    fun testFetchUserDetails() {
        model?.getUsers { users ->
            for (user in users) {
                val username = user.login
                if  (username == null) {
                    Assert.fail()
                }else {
                    userDetailsModel?.getUserData(username) {userDetails ->
                        Assert.assertEquals(userDetails.id, user.id)
                        val id = user.id
                        if (id == null) {
                            Assert.fail()
                        }else {
                            val retrievedUser = AppDatabase.getInstance(InstrumentationRegistry.getInstrumentation().context).getGithubUserDetails(id)
                            Assert.assertNotNull(retrievedUser)
                            Assert.assertEquals(retrievedUser.avatar_url, user.avatar_url)
                        }
                    }
                }
            }
        }
    }
}