package com.example.anees.hilt


import android.content.Context
import com.example.anees.data.local.database.AneesDao
import com.example.anees.data.local.database.AneesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

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



}