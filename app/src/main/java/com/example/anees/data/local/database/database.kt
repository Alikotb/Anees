package com.example.anees.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.anees.data.model.Sebiha
import com.example.anees.utils.Constants

@Database(entities =  [Sebiha::class, ], version =1 )
abstract class AneesDatabase : RoomDatabase(){
    abstract fun getDao(): AneesDao
    companion object{
        @Volatile
        private var instance: AneesDatabase? = null
        fun getInstance(context: Context): AneesDatabase {
            return instance ?: synchronized(this){
                val INSTANCE = Room.databaseBuilder(context, AneesDatabase::class.java, Constants.ROOM_DATABASE).build()
                instance = INSTANCE
                INSTANCE
            }
        }
    }
}