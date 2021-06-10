package com.sphtechapp.myapplicationsph.data.local

import androidx.room.*
import com.sphtechapp.myapplicationsph.data.remote.responses.RecordsData

@Dao
interface DataUsageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: ArrayList<RecordsData>): Array<Long>

    @Query("SELECT * FROM usage_data")
    fun findAll(): List<RecordsData>
}