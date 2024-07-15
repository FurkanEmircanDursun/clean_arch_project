package com.example.cityappcoroutineretrofitmvvmcompose.presentation.view

import com.example.cityappcoroutineretrofitmvvmcompose.domain.model.CityModel

data class CitiesState(

    val isLoading: Boolean=false,
    val cities:List<CityModel> = emptyList(),
    val error:String=""
)