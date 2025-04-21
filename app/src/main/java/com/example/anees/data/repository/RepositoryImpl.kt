package com.example.anees.data.repository

import com.example.anees.data.local.LocalDataSource
import com.example.anees.data.remote.RemoteDataSource
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : Repository {
}