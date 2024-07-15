package com.example.cityappcoroutineretrofitmvvmcompose.domain.use_case

import android.util.Log
import com.example.cityappcoroutineretrofitmvvmcompose.data.remote.dto.toCityList
import com.example.cityappcoroutineretrofitmvvmcompose.domain.model.CityModel
import com.example.cityappcoroutineretrofitmvvmcompose.domain.repository.CityRepository
import com.example.cityappcoroutineretrofitmvvmcompose.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOError
import javax.inject.Inject

class GetCityUseCase @Inject constructor(   private val repository: CityRepository) {


fun executeGetCities():Flow<Resource<List<CityModel>>> = flow{

try {
    emit(Resource.Loading())
    val cityList=repository.getCities()


    if(cityList.isSuccessful){
       emit(Resource.Success(cityList.body()?.toCityList()))
    }
    else{
        emit(Resource.Error("No Data Found"))
    }


}catch (e: IOError){
    emit(Resource.Error(e.localizedMessage?:"Error"))

}

}

}