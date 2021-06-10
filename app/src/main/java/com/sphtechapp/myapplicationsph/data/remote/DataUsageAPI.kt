package com.sphtechapp.myapplicationsph.data.remote

import com.sphtechapp.myapplicationsph.data.remote.responses.DataUsageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DataUsageAPI {

    @GET("api/action/datastore_search")
    suspend fun fetchDataUsage(
        @Query("resource_id") resourceId: String
    ): Response<DataUsageResponse>
}