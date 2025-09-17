package com.example.anees.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.anees.data.model.Ad3yaEntity
import com.example.anees.data.model.AzkarEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MahafogatDao {

    // Azkar
    @Insert
    suspend fun addAzkar(azkar: AzkarEntity)

    @Delete
    suspend fun deleteAzkar(azkar: AzkarEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM mahafogat_azkar WHERE category = :category)")
    suspend fun isAzkarSaved(category: String): Boolean

    @Query("SELECT * FROM mahafogat_azkar")
    fun getAllSavedAzkarFlow(): Flow<List<AzkarEntity>>

    // Ad3ya
    @Insert
    suspend fun addAd3ya(ad3ya: Ad3yaEntity)

    @Delete
    suspend fun deleteAd3ya(ad3ya: Ad3yaEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM mahafogat_ad3ya WHERE title = :title)")
    suspend fun isAd3yaSaved(title: String): Boolean

    @Query("SELECT * FROM mahafogat_ad3ya")
    fun getAllSavedAd3yaFlow(): Flow<List<Ad3yaEntity>>
}
