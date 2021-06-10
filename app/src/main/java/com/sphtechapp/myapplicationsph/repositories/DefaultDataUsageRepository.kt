package com.sphtechapp.myapplicationsph.repositories

import com.sphtechapp.myapplicationsph.data.local.DataUsageDao
import com.sphtechapp.myapplicationsph.data.remote.DataUsageAPI
import com.sphtechapp.myapplicationsph.data.remote.responses.DataUsageResponse
import com.sphtechapp.myapplicationsph.data.remote.responses.RecordsData
import com.sphtechapp.myapplicationsph.other.Resource
import javax.inject.Inject

class DefaultDataUsageRepository @Inject constructor(
    private val dataUsageDao: DataUsageDao,
    private val dataUsageAPI: DataUsageAPI
) : DataUsageRepository {

    override suspend fun insertDataUsageItems(data: ArrayList<RecordsData>) {
        dataUsageDao.insertAll(data)
    }

    override suspend fun fetchDataUsageItems(): List<RecordsData> {
        return dataUsageDao.findAll()
    }

    override suspend fun fetchDataUsage(resourceId: String): Resource<DataUsageResponse> {
        return try {
            val response = dataUsageAPI.fetchDataUsage(resourceId)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occured", null)
            } else {
                Resource.error("An unknown error occured", null)
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }
}














