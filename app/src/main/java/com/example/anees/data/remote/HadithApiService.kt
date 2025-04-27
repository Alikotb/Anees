package com.example.anees.data.remote

import com.example.anees.data.model.EditionResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HadithApiService {
    @GET("editions/ara-{edition}.json")
    suspend fun getSections(
        @Path("edition") edition: String
    ): EditionResponse

}