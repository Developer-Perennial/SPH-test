package com.sphtechapp.myapplicationsph.data.remote.responses

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class DataUsageResponse(
    @SerializedName("help") val help: String,
    @SerializedName("success") val success: Boolean,
    @SerializedName("result") val result: ResultData
)
data class ResultData(
    @SerializedName("resource_id") val resourceId: String,
    @SerializedName("fields") val fields: List<FieldsData>,
    @SerializedName("records") val records: List<RecordsData>,
    @SerializedName("_links") val links: LinksData,
    @SerializedName("limit") val limit: Int,
    @SerializedName("total") val total: Int
)
data class FieldsData(
    @SerializedName("type") val type: String,
    @SerializedName("id") val id: String
)
@Entity(tableName = "usage_data")
data class RecordsData(
    @PrimaryKey @SerializedName("_id") val id: Int,
    @SerializedName("volume_of_mobile_data") val volumeOfMobileData: String,
    @SerializedName("quarter") val quarter: String,
    @Ignore var year: Int,
    @Ignore var totalYearVolume: Double = 0.0,
    @Ignore var decreased: String = ""
) {
    constructor(id: Int, volumeOfMobileData: String, quarter: String) : this(id, volumeOfMobileData, quarter, 0, 0.0, "")
}
data class LinksData(
    @SerializedName("start") val start: String,
    @SerializedName("next") val next: String
)