package com.example.anees.data.repository

import com.example.anees.data.local.LocalDataSource
import com.example.anees.data.model.HadithBooksResponse
import com.example.anees.data.model.HadithResponse
import com.example.anees.data.model.HadithsResponse
import com.example.anees.data.model.Sebiha
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

    override suspend fun getBooks(): Flow<HadithBooksResponse> {
        return flowOf(remoteDataSource.getBooks())
    }

    override suspend fun getHadithsByRange(name: String, range: String): Flow<HadithsResponse> {
        return flowOf(remoteDataSource.getHadithsByRange(name, range))
    }

    override suspend fun getSpecificHadith(name: String, number: Int): Flow<HadithResponse> {
        return flowOf(remoteDataSource.getSpecificHadith(name, number))
    }
}