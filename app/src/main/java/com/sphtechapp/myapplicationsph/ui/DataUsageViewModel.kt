package com.sphtechapp.myapplicationsph.ui

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sphtechapp.myapplicationsph.data.remote.responses.DataUsageResponse
import com.sphtechapp.myapplicationsph.data.remote.responses.RecordsData
import com.sphtechapp.myapplicationsph.other.Constants
import com.sphtechapp.myapplicationsph.other.Event
import com.sphtechapp.myapplicationsph.other.Resource
import com.sphtechapp.myapplicationsph.repositories.DataUsageRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class DataUsageViewModel @ViewModelInject constructor(
    private val repository: DataUsageRepository
) : ViewModel() {

    private val fromYear = 2008
    private val toYear = 2018

    private val _dataLoading: ObservableField<Boolean> = ObservableField()
    val dataLoading: ObservableField<Boolean> = _dataLoading

    private val _dataUsageResponse = MutableLiveData<Event<Resource<DataUsageResponse>>>()
    val dataUsageResponse: LiveData<Event<Resource<DataUsageResponse>>> = _dataUsageResponse

    private val _insertDataUsageItemStatus = MutableLiveData<Event<Resource<List<RecordsData>>>>()
    val insertDataUsageItemStatus: LiveData<Event<Resource<List<RecordsData>>>> = _insertDataUsageItemStatus

    private val _fetchDataUsageItemStatus = MutableLiveData<Event<Resource<List<RecordsData>>>>()
    val fetchDataUsageItemStatus: LiveData<Event<Resource<List<RecordsData>>>> = _fetchDataUsageItemStatus

    fun insertDataUsageItemIntoDb(dataUsageItems: List<RecordsData>?) = GlobalScope.launch {
        repository.insertDataUsageItems(ArrayList(dataUsageItems))
        _insertDataUsageItemStatus.postValue(Event(Resource.success(dataUsageItems)))
    }

    fun fetchDataUsageItemFromDb() = GlobalScope.launch {
        _dataLoading.set(true)
        val dataItems = repository.fetchDataUsageItems()
        println("dataItems ${dataItems.size}")
        _fetchDataUsageItemStatus.postValue(Event(Resource.success(dataItems)))
    }

    fun fetchDataUsage() {
        _dataLoading.set(true)
        _dataUsageResponse.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.fetchDataUsage(Constants.RESOURCE_ID)
            _dataUsageResponse.value = Event(response)
            filterData(response.data?.result?.records)
        }
    }

    fun filterData(arrayList: List<RecordsData>?): Map<Int, RecordsData> {
        val filterArrayList = ArrayList<RecordsData>()
        var lastYear = 0
        var lastVolumeOfMobileData = 0.0
        arrayList?.forEach {
            it.year = Integer.parseInt(it.quarter.subSequence(0, 4).toString())
            if (it.year in fromYear..toYear) {
                it.totalYearVolume = it.volumeOfMobileData.toDouble()
                it.decreased =
                    when (it.year == lastYear && it.volumeOfMobileData.toDouble() < lastVolumeOfMobileData) {
                        true -> it.quarter.subSequence(5, 7).toString()
                        else -> ""
                    }
                lastYear = it.year
                lastVolumeOfMobileData = it.volumeOfMobileData.toDouble()
                filterArrayList.add(it)
            }
        }
        insertDataUsageItemIntoDb(filterArrayList)
        return filterArrayList.groupingBy(RecordsData::year)
            .aggregate { _, accumulator: RecordsData?, element: RecordsData, _ ->
                accumulator?.let {
                    it.copy(
                        totalYearVolume = it.totalYearVolume + element.totalYearVolume,
                        decreased = it.decreased + element.decreased
                    )
                } ?: element
            }
    }
}













