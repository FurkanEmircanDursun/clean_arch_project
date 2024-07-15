package com.example.cityappcoroutineretrofitmvvmcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.cityappcoroutineretrofitmvvmcompose.presentation.theme.CityAppCoroutineRetrofitMVVMComposeTheme
import com.example.cityappcoroutineretrofitmvvmcompose.presentation.view.CityListScreen
import com.example.cityappcoroutineretrofitmvvmcompose.presentation.view.CityListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val cityListViewModel: CityListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            CityAppCoroutineRetrofitMVVMComposeTheme {
        CityListScreen(cityListViewModel = cityListViewModel)

            }
        }
    }

    
}
