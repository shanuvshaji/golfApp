package com.example.sampleapp.data.model.dto

data class CourseResponseDTO(
    val courses: List<Course>
)

data class Course(
    val club_name: String,
    val course_name: String,
    val id: Int,
    val location: Location
)

data class Location(
    val address: String?=""
)