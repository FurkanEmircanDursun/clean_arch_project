package com.example.cityappcoroutineretrofitmvvmcompose.data.remote.dto

data class Data(
    val altitude: Int,
    val area: Int,
    val areaCode: List<Int>,
    val coordinates: Coordinates,
    val districts: List<District>,
    val id: Int,
    val isMetropolitan: Boolean,
    val maps: Maps,
    val name: String,
    val nuts: Nuts,
    val population: Int,
    val region: Region
)