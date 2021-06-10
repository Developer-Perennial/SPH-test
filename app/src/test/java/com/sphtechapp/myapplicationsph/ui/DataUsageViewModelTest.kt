package com.sphtechapp.myapplicationsph.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.sphtechapp.myapplicationsph.MainCoroutineRule
import com.sphtechapp.myapplicationsph.data.remote.responses.RecordsData
import com.sphtechapp.myapplicationsph.getOrAwaitValueTest
import com.sphtechapp.myapplicationsph.other.Status
import com.sphtechapp.myapplicationsph.repositories.FakeDataUsageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class DataUsageViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel1: DataUsageViewModel
    private lateinit var viewModel2: DataUsageViewModel
    private val viewModel3 = Mockito.mock(DataUsageViewModel::class.java)

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
    fun `fetch filtered items`() {
        viewModel3.fromYear = 2008
        viewModel3.toYear = 2020
        val dataUsageItem1 = RecordsData(1,"1.00001", "2016-Q1")
        val dataUsageItem2 = RecordsData(2,"1.20001", "2019-Q2")
        val allDataArray = arrayListOf(dataUsageItem1, dataUsageItem2)
        val filteredData = viewModel3.filterData(allDataArray)
        Assert.assertEquals( 2, filteredData.size)
        Assert.assertEquals( 2016, filteredData[0].year)
        val groupData = viewModel3.groupData(allDataArray)
        Assert.assertEquals( 2, groupData.size)
        Assert.assertEquals( 1, groupData[2016]?.id)
    }
}













