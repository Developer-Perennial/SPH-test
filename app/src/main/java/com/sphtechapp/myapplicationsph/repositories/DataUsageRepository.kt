package com.sphtechapp.myapplicationsph.repositories

import com.sphtechapp.myapplicationsph.data.remote.responses.DataUsageResponse
import com.sphtechapp.myapplicationsph.data.remote.responses.RecordsData
import com.sphtechapp.myapplicationsph.other.Resource

interface DataUsageRepository {

    suspend fun insertDataUsageItems(data: ArrayList<RecordsData>)

    suspend fun fetchDataUsageItems(): List<RecordsData>

    suspend fun fetchDataUsage(resourceId: String): Resource<DataUsageResponse>
}