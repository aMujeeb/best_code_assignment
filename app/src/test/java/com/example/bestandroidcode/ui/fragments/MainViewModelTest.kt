package com.example.bestandroidcode.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.bestandroidcode.data.RemoteDataSource
import com.example.bestandroidcode.data.db.dao.CatDao
import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.ui.fragments.main.MainViewModel
import com.example.bestandroidcode.util.MainCoroutineRule
import com.example.bestandroidcode.util.TestCoroutineRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    val mTestCoroutineRule = MainCoroutineRule()

    private lateinit var mMainViewModel : MainViewModel
    private lateinit var catDao: CatDao
    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        catDao = mockk<CatDao>()
        remoteDataSource = mockk<RemoteDataSource>()
        mMainViewModel = MainViewModel(remoteDataSource, catDao)
    }

    @Test
    fun testRequestRandomCatImage() = runBlockingTest {
        ///every {remoteDataSource.requestRandomCat()} returns List<Cat>
        mMainViewModel.requestRandomCatImage()
    }

    @After
    fun afterTests() {
        unmockkAll()
    }
}