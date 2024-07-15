package com.example.cityappcoroutineretrofitmvvmcompose.data.remote

import com.example.cityappcoroutineretrofitmvvmcompose.data.remote.dto.CitiesDto
import com.example.cityappcoroutineretrofitmvvmcompose.domain.model.CityModel
import retrofit2.Response
import retrofit2.http.GET

interface CityAPI {

    @GET("provinces")
    suspend fun getCities(): Response<CitiesDto>

}