package com.example.anees.data.repository

import com.example.anees.data.local.LocalDataSource
import com.example.anees.data.model.EditionResponse
import com.example.anees.data.model.Sebiha
import com.example.anees.data.model.TafsierModel
import com.example.anees.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : Repository {


    override suspend fun addSebiha(sebiha: Sebiha) {
        localDataSource.insertSebiha(sebiha)
    }

    override fun getSebiha(): Flow<Sebiha> {
        return localDataSource.getSebiha()
    }

    override suspend fun getAllSections(name: String): Flow<EditionResponse> {
        return flowOf(remoteDataSource.getAllSections(name))
    }

    override suspend fun getAllTafsier(name: String): Flow<TafsierModel> {
        return flowOf(remoteDataSource.getAllTafsier(name))
    }


    override fun saveData(key: String, value: Any) {
        localDataSource.saveData(key, value)
    }

    override fun <T> fetchData(key: String, defaultValue: T): T {
        return localDataSource.fetchData(key,defaultValue)
    }
}