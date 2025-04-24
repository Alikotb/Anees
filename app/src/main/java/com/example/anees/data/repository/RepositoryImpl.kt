package com.example.anees.data.repository

import com.example.anees.data.local.LocalDataSource
import com.example.anees.data.model.EditionResponse
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

    override suspend fun getAllSections(name: String): Flow<EditionResponse> {
        return flowOf(remoteDataSource.getAllSections(name))
    }

    override suspend fun getAuthorHadithsBySection(
        name: String,
        author: String
    ): Flow<HadithsResponse> {
        return flowOf(remoteDataSource.getAuthorHadithsBySection(name, author))
    }

}