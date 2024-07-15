package com.example.cityappcoroutineretrofitmvvmcompose.data.repository

import com.example.cityappcoroutineretrofitmvvmcompose.data.remote.CityAPI
import com.example.cityappcoroutineretrofitmvvmcompose.data.remote.dto.CitiesDto
import com.example.cityappcoroutineretrofitmvvmcompose.domain.repository.CityRepository
import retrofit2.Response
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(private val api:CityAPI)  :CityRepository {
    override suspend fun getCities(): Response<CitiesDto> {

        return api.getCities()
    }
}