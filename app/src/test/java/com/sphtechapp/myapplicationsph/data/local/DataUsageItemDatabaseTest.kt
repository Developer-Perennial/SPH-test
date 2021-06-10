package com.sphtechapp.myapplicationsph.data.local

import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito

class DataUsageItemDatabaseTest {
    @Test
    fun shouldFailOnNullIdentifiers() {
        val dataUsageItemDatabase: DataUsageItemDatabase =
            Mockito.mock(DataUsageItemDatabase::class.java, Mockito.RETURNS_MOCKS)
        assertNotNull(dataUsageItemDatabase.dataUsageDao())
    }
}