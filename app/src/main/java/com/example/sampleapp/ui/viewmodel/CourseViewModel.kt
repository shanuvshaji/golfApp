package com.example.sampleapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.GolfApp
import com.example.sampleapp.domain.model.CourseDomain
import com.example.sampleapp.data.repository.CourseRepositoryImpl
import com.example.sampleapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CourseViewModel @Inject constructor(
    private val repository: CourseRepositoryImpl
) : ViewModel() {

    private val _courses = MutableLiveData<Resource<List<CourseDomain>>>()
    val courses: LiveData<Resource<List<CourseDomain>>> get() = _courses

    fun fetchCourses(query: String) = viewModelScope.launch {
        repository.getCourses(query).collect {
            _courses.postValue(it)
        }
    }
    fun searchCourses(query: String) = viewModelScope.launch {
       val result= repository.getCourses(GolfApp.API_KEY).asLiveData()
       // _courses.postValue(result)

    }
}

