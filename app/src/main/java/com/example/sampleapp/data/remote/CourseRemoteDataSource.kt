package com.example.sampleapp.data.remote

import com.example.sampleapp.data.model.dto.CourseResponseDTO
import retrofit2.Response
import javax.inject.Inject

class CourseRemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun fetchCourses(apiKey: String, query: String): Response<CourseResponseDTO> = apiService.getCourses(apiKey,query)
}