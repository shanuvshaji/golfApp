package com.example.sampleapp.domain.repository

import com.example.sampleapp.domain.model.CourseDomain
import com.example.sampleapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    suspend fun getCourses(apiKey: String): Flow<Resource<List<CourseDomain>>>
}