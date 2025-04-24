package com.example.anees.data.remote

import com.example.anees.data.model.HadithBooksResponse
import com.example.anees.data.model.HadithResponse
import com.example.anees.data.model.HadithsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HadithApiService {
    @GET("books")
    suspend fun getBooks(): HadithBooksResponse

    @GET("books/{name}")
    suspend fun getHadithsByRange(
        @Path("name") name: String,
        @Query("range") range: String
    ): HadithsResponse

    @GET("books/{name}/{number}")
    suspend fun getSpecificHadith(
        @Path("name") name: String,
        @Path("number") number: Int
    ): HadithResponse
}