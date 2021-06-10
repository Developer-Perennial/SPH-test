package com.sphtechapp.myapplicationsph.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sphtechapp.myapplicationsph.R
import com.sphtechapp.myapplicationsph.data.remote.responses.RecordsData
import com.sphtechapp.myapplicationsph.databinding.ActivityMainBinding
import com.sphtechapp.myapplicationsph.other.EventObserver
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
            Toast.makeText(
                applicationContext,
                getString(R.string.volume_down, it.quarter, it.year),
                Toast.LENGTH_LONG
            ).show()
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
                loadAdapter(recordAPIDataList.data?.result?.records)
            })
    }

    private fun loadAdapter(dbDataList: List<RecordsData>?) {
        dataUsageData.putAll(viewModel.filterData(dbDataList))
        dataBinding.rvDataUsageList.adapter?.notifyDataSetChanged()
        viewModel.dataLoading.set(false)
    }
}