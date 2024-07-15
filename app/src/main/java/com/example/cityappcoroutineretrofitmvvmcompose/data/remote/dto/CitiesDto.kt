package com.example.cityappcoroutineretrofitmvvmcompose.data.remote.dto

import com.example.cityappcoroutineretrofitmvvmcompose.domain.model.CityModel


data class CitiesDto(
    val `data`: List<Data>,
    val status: String
)

fun CitiesDto.toCityList(): List<CityModel> {
    return data.map { data ->

        CityModel(
            data.id,
            data.name,
            data.population,
            data.area,
            data.altitude,
            data.isMetropolitan,
            data.region,
            data.districts
        )
    }
}