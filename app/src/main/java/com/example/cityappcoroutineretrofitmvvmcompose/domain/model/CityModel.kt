package com.example.cityappcoroutineretrofitmvvmcompose.domain.model

import com.example.cityappcoroutineretrofitmvvmcompose.data.remote.dto.District
import com.example.cityappcoroutineretrofitmvvmcompose.data.remote.dto.Region


data class CityModel(
    val id: Int,
    val name: String,
    val population: Int,
    val area: Int,
    val altitude: Int,
    val isMetropolitan: Boolean,
    val region: Region,
    val districts: List<District>
)


