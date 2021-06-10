package com.sphtechapp.myapplicationsph.other

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class ConstantsTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testValues() {
        assertEquals("data_usage_db", Constants.DATABASE_NAME)
        assertEquals("https://data.gov.sg/", Constants.BASE_URL)
    }
}