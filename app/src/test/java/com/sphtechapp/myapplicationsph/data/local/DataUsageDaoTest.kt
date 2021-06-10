package com.sphtechapp.myapplicationsph.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sphtechapp.myapplicationsph.data.remote.responses.RecordsData
import org.hamcrest.core.IsNull
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class DataUsageDaoTest{

    private val dataUsageAmountDao = Mockito.mock(DataUsageDao::class.java)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun loadDataFromDataBase() {
        val recordsData = RecordsData(1, "2016-Q1","1.00001")
        val recordsDataArray = arrayListOf(recordsData)
        Mockito.`when`(dataUsageAmountDao.findAll()).thenReturn(recordsDataArray)

        Assert.assertThat(recordsDataArray, IsNull.notNullValue())
    }

    @Test
    fun saveDataInDatabase() {
        val recordsData = RecordsData(1, "2016-Q1","1.00001")
        val successInsert = arrayOf(1L)
        Mockito.`when`(dataUsageAmountDao.insertAll(arrayListOf(recordsData))).thenReturn(successInsert)

        Assert.assertThat(successInsert, IsNull.notNullValue())
    }
}