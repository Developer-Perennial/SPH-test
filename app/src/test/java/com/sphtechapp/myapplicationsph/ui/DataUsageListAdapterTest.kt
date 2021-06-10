package com.sphtechapp.myapplicationsph.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.common.truth.Truth
import com.sphtechapp.myapplicationsph.data.remote.responses.RecordsData
import com.sphtechapp.myapplicationsph.databinding.ItemDataUsageBinding
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock


class DataUsageListAdapterTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dataUsageListAdapter: DataUsageListAdapter

    @Before
    fun setup() {
        val dataUsageData =  HashMap<Int, RecordsData>()
        dataUsageData[2016] = RecordsData(1,"1.00001", "2016-Q1")
        dataUsageListAdapter = DataUsageListAdapter(dataUsageData) {
            Truth.assertThat(it.id).isEqualTo(1)
        }
    }

    @Test
    fun `test data`() {
        Truth.assertThat(dataUsageListAdapter.itemCount).isEqualTo(1)
    }

}