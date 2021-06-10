package com.sphtechapp.myapplicationsph.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sphtechapp.myapplicationsph.data.remote.responses.RecordsData
import com.sphtechapp.myapplicationsph.databinding.ItemDataUsageBinding

class DataUsageListAdapter(
    private val dataUsageData: HashMap<Int, RecordsData>,
    private val clickListener: (RecordsData) -> Unit
) :
    RecyclerView.Adapter<DataUsageListAdapter.DataAmountViewHolder>() {

    class DataAmountViewHolder(private val itemDataUsageBinding: ItemDataUsageBinding) :
        RecyclerView.ViewHolder(itemDataUsageBinding.root) {
        fun bind(
            data: RecordsData,
            clickListener: (RecordsData) -> Unit
        ) {
            itemDataUsageBinding.data = data
            itemDataUsageBinding.dataClickHandler = DataClickHandler(clickListener)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataAmountViewHolder {
        return DataAmountViewHolder(
            ItemDataUsageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DataAmountViewHolder, position: Int) {
        dataUsageData[ArrayList<Int>(dataUsageData.keys).sorted()[position]]?.let { holder.bind(it, clickListener) }
    }

    override fun getItemCount() = dataUsageData.size
}

class DataClickHandler(private val clickListener: (RecordsData) -> Unit) {
    fun onClick(recordsData: RecordsData) {
        clickListener.invoke(recordsData)
    }
}