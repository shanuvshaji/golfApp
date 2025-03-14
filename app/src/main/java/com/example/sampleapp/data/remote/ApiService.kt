package com.example.sampleapp.data.remote

import com.example.sampleapp.data.model.dto.CourseResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("v1/search")
    suspend fun getCourses(
        @Header("Authorization") apiKey: String,
        @Query("search_query") searchQuery: String
    ): Response<CourseResponseDTO>
}
