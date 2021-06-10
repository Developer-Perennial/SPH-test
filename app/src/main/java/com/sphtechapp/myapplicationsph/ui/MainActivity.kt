package com.sphtechapp.myapplicationsph.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.sphtechapp.myapplicationsph.R
import com.sphtechapp.myapplicationsph.data.remote.responses.RecordsData
import com.sphtechapp.myapplicationsph.databinding.ActivityMainBinding
import com.sphtechapp.myapplicationsph.other.EventObserver
import com.sphtechapp.myapplicationsph.other.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var viewModel: DataUsageViewModel

    private lateinit var dataBinding: ActivityMainBinding
    private var dataUsageData = HashMap<Int, RecordsData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(DataUsageViewModel::class.java)

        dataBinding = DataBindingUtil
            .setContentView(this, R.layout.activity_main)
        dataBinding.lifecycleOwner = this
        dataBinding.viewModel = viewModel

        setUpObservers()
        setUpList()

        viewModel.fetchDataUsageItemFromDb()
    }

    private fun setUpList() {
        val rvAdapter = DataUsageListAdapter(dataUsageData) {
            Snackbar.make(dataBinding.root, getString(R.string.volume_down, it.quarter, it.year), Snackbar.LENGTH_LONG).show()
        }
        dataBinding.rvDataUsageList.adapter = rvAdapter
    }

    private fun setUpObservers() {
        viewModel.fetchDataUsageItemStatus.observe(
            this,
            EventObserver { recordDBDataList ->
                println("recordDBDataList.data ${recordDBDataList.data}")
                when (recordDBDataList.data == null || recordDBDataList.data.isEmpty()) {
                    true -> viewModel.fetchDataUsage()
                    else -> loadAdapter(recordDBDataList.data)
                }
            })
        viewModel.dataUsageResponse.observe(
            this,
            EventObserver { recordAPIDataList ->
                println(recordAPIDataList.status)
                when {
                    recordAPIDataList.status == Status.ERROR -> {
                        viewModel.dataLoading.set(false)
                        Snackbar.make(dataBinding.root, getString(R.string.data_loading_failed), Snackbar.LENGTH_LONG).show()
                    }
                    recordAPIDataList.status == Status.SUCCESS -> {
                        loadAdapter(recordAPIDataList.data?.result?.records)
                    }
                }
            })
    }

    private fun loadAdapter(dbDataList: List<RecordsData>?) {
        dataUsageData.putAll(viewModel.groupData(viewModel.filterData(dbDataList)))
        dataBinding.rvDataUsageList.adapter?.notifyDataSetChanged()
        viewModel.dataLoading.set(false)
    }
}