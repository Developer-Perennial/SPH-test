package com.sphtechapp.myapplicationsph.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sphtechapp.myapplicationsph.data.remote.responses.RecordsData
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class DataUsageItemTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testValues() {
        val dataUsageItem = RecordsData(1,"1.00001", "2016-Q1")
        assertEquals(1, dataUsageItem.id)
        assertEquals("2016-Q1", dataUsageItem.quarter)
        assertEquals("1.00001", dataUsageItem.volumeOfMobileData)
    }
}