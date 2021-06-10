package com.sphtechapp.myapplicationsph.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sphtechapp.myapplicationsph.MainCoroutineRule
import com.sphtechapp.myapplicationsph.repositories.FakeDataUsageRepository
import com.google.common.truth.Truth.assertThat
import com.sphtechapp.myapplicationsph.data.remote.responses.RecordsData
import com.sphtechapp.myapplicationsph.getOrAwaitValueTest
import com.sphtechapp.myapplicationsph.other.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DataUsageViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel1: DataUsageViewModel
    private lateinit var viewModel2: DataUsageViewModel

    private lateinit var fakeDataUsageRepository1: FakeDataUsageRepository
    private lateinit var fakeDataUsageRepository2: FakeDataUsageRepository

    @Before
    fun setup() {
        fakeDataUsageRepository1 = FakeDataUsageRepository()
        fakeDataUsageRepository2 = FakeDataUsageRepository()
        fakeDataUsageRepository2.setShouldReturnNetworkError(true)
        viewModel1 = DataUsageViewModel(fakeDataUsageRepository1)
        viewModel2 = DataUsageViewModel(fakeDataUsageRepository2)
    }

    @Test
    fun `insert single item`() {
        val dataUsageItem = RecordsData(1,"1.00001", "2016-Q1")
        viewModel1.insertDataUsageItemIntoDb(arrayListOf(dataUsageItem))

        val value = viewModel1.insertDataUsageItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `insert multiple items`() {

        val dataUsageItem1 = RecordsData(1,"1.00001", "2016-Q1")
        val dataUsageItem2 = RecordsData(2,"1.20001", "2016-Q2")

        viewModel1.insertDataUsageItemIntoDb(arrayListOf(dataUsageItem1, dataUsageItem2))

        val value = viewModel1.insertDataUsageItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `fetch items`() {
        viewModel1.fetchDataUsageItemFromDb()

        val value = viewModel1.fetchDataUsageItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `fetch items from api`() {
        viewModel1.fetchDataUsage()

        val value = viewModel1.dataUsageResponse.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `fetch items from api failed response`() {
        viewModel2.fetchDataUsage()

        val value = viewModel2.dataUsageResponse.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `fetch filter items`() {
        val dataUsageItem1 = RecordsData(1,"1.00001", "2016-Q1")
        val dataUsageItem2 = RecordsData(2,"1.20001", "2016-Q2")
        viewModel2.filterData(arrayListOf(dataUsageItem1, dataUsageItem2))
    }
}













