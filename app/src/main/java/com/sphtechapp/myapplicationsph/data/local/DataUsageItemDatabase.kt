package com.sphtechapp.myapplicationsph.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sphtechapp.myapplicationsph.data.remote.responses.RecordsData

@Database(
    entities = [RecordsData::class],
    version = 1,
    exportSchema = false
)
abstract class DataUsageItemDatabase : RoomDatabase() {

    abstract fun dataUsageDao(): DataUsageDao
}