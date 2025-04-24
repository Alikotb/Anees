package com.example.anees.hilt


import android.content.Context
import com.example.anees.data.local.database.AneesDao
import com.example.anees.data.local.database.AneesDatabase
import com.example.anees.data.remote.HadithApiService
import com.example.anees.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppProviderModule  {
    @Provides
     fun bindDatabase(@ApplicationContext context: Context): AneesDatabase {
        return AneesDatabase.getInstance(context)
    }

    @Provides
    fun bindDao(db: AneesDatabase): AneesDao {
        return db.getDao()
    }

    @Provides
    @Singleton
    @Named("HadithApi")
    fun provideHadithRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.HTTPS_API_HADITH_GADING_DEV_)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideHadithApiService(@Named("HadithApi") retrofit: Retrofit): HadithApiService =
        retrofit.create(HadithApiService::class.java)
}