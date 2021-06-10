package com.sphtechapp.myapplicationsph.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sphtechapp.myapplicationsph.data.remote.responses.*
import com.sphtechapp.myapplicationsph.other.Resource

class FakeDataUsageRepository : DataUsageRepository {

    private val dataUsageItems = mutableListOf<RecordsData>()

    private val observableDataUsageItems = MutableLiveData<List<RecordsData>>(dataUsageItems)

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData() {
        observableDataUsageItems.postValue(dataUsageItems)
    }

    override suspend fun insertDataUsageItems(data: ArrayList<RecordsData>) {
        dataUsageItems.addAll(data)
        refreshLiveData()
    }

    override suspend fun fetchDataUsageItems(): List<RecordsData> {
        observableDataUsageItems.value = dataUsageItems
        return dataUsageItems
    }

    override suspend fun fetchDataUsage(resourceId: String): Resource<DataUsageResponse> {
        return if(shouldReturnNetworkError) {
            Resource.error("Error", null)
        } else {
            val fieldsData = FieldsData("int4", "_id" )
            val linksData = LinksData("/api/action/datastore_search?limit=10&resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f", "/api/action/datastore_search?offset=10&limit=10&resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f" )
            val recordsData = RecordsData(1, "0.000384", "2004-Q3")
            val resultData = ResultData("a807b7ab-6cad-4aa6-87d0-e283a7353a0f", arrayListOf(fieldsData), arrayListOf(recordsData), linksData, 10, 59 )
            val dataUsageResponse = DataUsageResponse("https://data.gov.sg/api/3/action/help_show?name=datastore_search", true, resultData)
            Resource.success(dataUsageResponse)
        }
    }
}











