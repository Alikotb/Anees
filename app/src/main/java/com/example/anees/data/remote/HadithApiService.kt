package com.example.anees.data.remote

import com.example.anees.data.model.EditionResponse
import com.example.anees.data.model.HadithsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HadithApiService {
    @GET("editions/ara-{edition}.json")
    suspend fun getSections(
        @Path("edition") edition: String
    ): EditionResponse

    @GET("editions/{edition}/sections/{sectionId}.json")
    suspend fun getHadithsBySection(
        @Path("edition") edition: String,
        @Path("sectionId") sectionId: String
    ): HadithsResponse
}