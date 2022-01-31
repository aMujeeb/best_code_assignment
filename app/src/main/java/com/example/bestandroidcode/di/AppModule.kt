package com.example.bestandroidcode.di

import com.example.bestandroidcode.network.CatDataRepositoryImpl
import com.example.bestandroidcode.network.api.CatAPI
import com.example.bestandroidcode.network.repository.CatDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideLoginInterceptor() : OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(httpLoggingInterceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideCatApi(builder : OkHttpClient) : CatAPI {
        return Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(builder)
            .build()
            .create(CatAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideCatRepository(api : CatAPI) : CatDataRepository {
        return CatDataRepositoryImpl(api)
    }
}