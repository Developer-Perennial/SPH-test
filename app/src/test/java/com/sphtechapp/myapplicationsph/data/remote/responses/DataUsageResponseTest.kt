package com.sphtechapp.myapplicationsph.data.remote.responses

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class DataUsageResponseTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testValues() {
        val fieldsData = FieldsData("int4", "_id" )
        val linksData = LinksData("/api/action/datastore_search?limit=10&resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f", "/api/action/datastore_search?offset=10&limit=10&resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f" )
        val recordsData = RecordsData(1, "0.000384", "2004-Q3")
        val resultData = ResultData("a807b7ab-6cad-4aa6-87d0-e283a7353a0f", arrayListOf(fieldsData), arrayListOf(recordsData), linksData, 10, 59 )
        val dataUsageResponse = DataUsageResponse("https://data.gov.sg/api/3/action/help_show?name=datastore_search", true, resultData)
        assertEquals("https://data.gov.sg/api/3/action/help_show?name=datastore_search", dataUsageResponse.help)
        assertEquals(true, dataUsageResponse.success)
        assertEquals(1, dataUsageResponse.result.records.size)
        assertEquals(1, dataUsageResponse.result.records[0].id)
        assertEquals("0.000384", dataUsageResponse.result.records[0].volumeOfMobileData)
        assertEquals("2004-Q3", dataUsageResponse.result.records[0].quarter)
    }
}