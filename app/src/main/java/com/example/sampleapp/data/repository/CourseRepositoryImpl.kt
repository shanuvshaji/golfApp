package com.example.sampleapp.data.repository

import com.example.sampleapp.BuildConfig
import com.example.sampleapp.domain.model.CourseDomain
import com.example.sampleapp.data.remote.CourseRemoteDataSource
import com.example.sampleapp.domain.repository.CourseRepository
import com.example.sampleapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val remoteDataSource: CourseRemoteDataSource
) : CourseRepository {

    override suspend fun getCourses(query: String): Flow<Resource<List<CourseDomain>>> = flow {
        emit(Resource.Loading())
        try {
            val response = remoteDataSource.fetchCourses(BuildConfig.GOLF_API_KEY,query)
            if (response.isSuccessful && response.body() != null) {

                // Converting dto to domain model

                val domainCourses = response.body()!!.courses.map {
                    CourseDomain(
                        id = it.id,
                        clubName = it.club_name,
                        courseName = it.course_name,
                        address = it.location.address
                    )
                }
                emit(Resource.Success(domainCourses))
            } else
                emit(Resource.Error("Failed to fetch courses: ${response.errorBody()}"))

        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch courses: ${e.message}"))
        }
    }
}
