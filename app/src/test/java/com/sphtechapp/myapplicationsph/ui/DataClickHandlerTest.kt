package com.sphtechapp.myapplicationsph.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.sphtechapp.myapplicationsph.data.remote.responses.RecordsData
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DataClickHandlerTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dataClickHandler: DataClickHandler
    private lateinit var clickListener: (RecordsData) -> Unit

    @Before
    fun setup() {
        clickListener = {
            Truth.assertThat(it.id).isEqualTo(1)
        }
        dataClickHandler = DataClickHandler(clickListener)
    }

    @Test
    fun `click test`() {
        val dataUsageItem = RecordsData(1,"1.00001", "2016-Q1")
        dataClickHandler.onClick(dataUsageItem)
        clickListener.invoke(dataUsageItem)
    }
}