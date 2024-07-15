package com.example.cityappcoroutineretrofitmvvmcompose.domain.repository

import com.example.cityappcoroutineretrofitmvvmcompose.data.remote.dto.CitiesDto
import retrofit2.Response

interface CityRepository {



    suspend fun getCities(): Response<CitiesDto>
}