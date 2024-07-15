package com.example.cityappcoroutineretrofitmvvmcompose.presentation.view

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cityappcoroutineretrofitmvvmcompose.data.remote.CityAPI
import com.example.cityappcoroutineretrofitmvvmcompose.data.remote.dto.toCityList
import com.example.cityappcoroutineretrofitmvvmcompose.domain.model.CityModel
import com.example.cityappcoroutineretrofitmvvmcompose.domain.use_case.GetCityUseCase
import com.example.cityappcoroutineretrofitmvvmcompose.util.Constant.BASE_URL
import com.example.cityappcoroutineretrofitmvvmcompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
@HiltViewModel
class CityListViewModel @Inject constructor(private val getCitiesUseCase: GetCityUseCase)


    : ViewModel() {


    private val _state = mutableStateOf<CitiesState>(CitiesState())
    val state: State<CitiesState> get() = _state
    private var job: Job? = null

    private fun getCities() {
        job?.cancel()

        job = getCitiesUseCase.executeGetCities().onEach {

            when (it) {
                is Resource.Success -> {
                    _state.value = CitiesState(cities = it.data ?: emptyList())

                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(error = it.message ?: "Error!")
                }

                is Resource.Loading -> {

                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    private val _cityModel: MutableLiveData<List<CityModel>> = MutableLiveData()


    val cityModel: LiveData<List<CityModel>> get() = _cityModel


    init {
        getCities()
    }

    private fun loadCities() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CityAPI::class.java)

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val response = retrofit.getCities()

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    apiResponse?.toCityList()
                        .let {
                            _cityModel.postValue(it)
                        }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("CityData", errorBody ?: "Unknown error")
                }

            } catch (e: Exception) {
                Log.e("CityData", "Data fetch failed: ${e.message}", e)
            }
        }
    }

}

