package com.example.cityappcoroutineretrofitmvvmcompose.data.dependencyInjection

import com.example.cityappcoroutineretrofitmvvmcompose.data.remote.CityAPI
import com.example.cityappcoroutineretrofitmvvmcompose.data.repository.CityRepositoryImpl
import com.example.cityappcoroutineretrofitmvvmcompose.domain.repository.CityRepository
import com.example.cityappcoroutineretrofitmvvmcompose.util.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideCityApi(): CityAPI {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CityAPI::class.java)
    }

    @Provides
    @Singleton
    fun ProvideCityRepository(api: CityAPI):CityRepository{

        return CityRepositoryImpl(api)
    }
}