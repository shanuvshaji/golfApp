package com.example.sampleapp.di

import android.util.Log
import com.example.sampleapp.BuildConfig
import com.example.sampleapp.data.remote.ApiService
import com.example.sampleapp.data.remote.CourseRemoteDataSource
import com.example.sampleapp.data.repository.CourseRepositoryImpl
import com.example.sampleapp.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHttpClient( ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor { message -> Log.d("ApiService", message) }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return builder.build()
    }
    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create(GsonBuilder().setDateFormat("dd-MM-yyyy").create())

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.baseURL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    fun provideRemoteDataSource(apiService: ApiService) =
        CourseRemoteDataSource(apiService)

    @Provides
    fun provideRepository(remoteDataSource: CourseRemoteDataSource) =
        CourseRepositoryImpl(remoteDataSource)

}
