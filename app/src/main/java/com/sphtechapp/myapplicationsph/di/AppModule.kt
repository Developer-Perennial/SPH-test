package com.sphtechapp.myapplicationsph.di

import android.content.Context
import androidx.room.Room
import com.sphtechapp.myapplicationsph.data.remote.DataUsageAPI
import com.sphtechapp.myapplicationsph.other.Constants.BASE_URL
import com.sphtechapp.myapplicationsph.other.Constants.DATABASE_NAME
import com.sphtechapp.myapplicationsph.repositories.DefaultDataUsageRepository
import com.sphtechapp.myapplicationsph.repositories.DataUsageRepository
import com.sphtechapp.myapplicationsph.data.local.DataUsageDao
import com.sphtechapp.myapplicationsph.data.local.DataUsageItemDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDataUsageItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, DataUsageItemDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDefaultDataUsageRepository(
        dao: DataUsageDao,
        api: DataUsageAPI
    ) = DefaultDataUsageRepository(dao, api) as DataUsageRepository

    @Singleton
    @Provides
    fun provideDataUsageDao(
        database: DataUsageItemDatabase
    ) = database.dataUsageDao()

    @Singleton
    @Provides
    fun provideDataUsageApi(): DataUsageAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(DataUsageAPI::class.java)
    }
}

















