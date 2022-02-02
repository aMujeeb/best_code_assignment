package com.example.bestandroidcode.data

import android.app.Application
import androidx.room.Room
import com.example.bestandroidcode.data.db.BestAppCatDb
import com.example.bestandroidcode.data.db.dao.CatDao
import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.util.MainCoroutineRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CatDaoTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mTestCoroutineRule = MainCoroutineRule()

    private lateinit var catDao: CatDao
    private lateinit var db: BestAppCatDb

    @Before
    fun createDb() {
        MockitoAnnotations.initMocks(this)
        val context = Mockito.mock(Application::class.java)
        db = Room.inMemoryDatabaseBuilder(
            context, BestAppCatDb::class.java
        ).build()
        catDao = db.getCatDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @ExperimentalCoroutinesApi
    @Throws(Exception::class)
    fun testInsertCat() {
        //Must overcome java.lang.RuntimeException: Method getMainLooper in android.os.Looper not mocked
        runBlocking(Dispatchers.IO) {
            var testCat = Cat(
                "2222",
                "www.test.com",
                300,
                300
            )
            catDao.insertFavouriteCat(testCat)
            Assert.assertEquals(1, catDao.insertFavouriteCat(testCat))
        }
    }
}