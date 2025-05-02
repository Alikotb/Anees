package com.example.anees.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.anees.data.local.database.dao.AneesDao
import com.example.anees.data.local.database.dao.TafsirDao
import com.example.anees.data.model.Sebiha
import com.example.anees.data.model.TafsierModel
import com.example.anees.data.model.converter.TafsierConverter
import com.example.anees.utils.Constants

@TypeConverters(TafsierConverter::class)
@Database(entities =  [Sebiha::class,TafsierModel::class ], version =1 )
abstract class AneesDatabase : RoomDatabase(){
    abstract fun getDao(): AneesDao
    abstract fun getTafsirDao(): TafsirDao
    companion object{
        @Volatile
        private var instance: AneesDatabase? = null
        fun getInstance(context: Context): AneesDatabase {
            return instance ?: synchronized(this){
                val INSTANCE = Room.databaseBuilder(context, AneesDatabase::class.java, Constants.ROOM_DATABASE).fallbackToDestructiveMigration().build()
                instance = INSTANCE
                INSTANCE
            }
        }
    }
}