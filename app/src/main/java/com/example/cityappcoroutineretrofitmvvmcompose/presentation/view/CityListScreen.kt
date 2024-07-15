package com.example.cityappcoroutineretrofitmvvmcompose.presentation.view

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cityappcoroutineretrofitmvvmcompose.domain.model.CityModel
import kotlin.random.Random
@Composable
fun CityListScreen(
    cityListViewModel: CityListViewModel = hiltViewModel()
) {
    val state = cityListViewModel.state.value

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        var searchQuery by remember { mutableStateOf("") }
        val cityModels = state.cities
        val filteredCityModels =
            cityModels.filter { it.name.contains(searchQuery, ignoreCase = true) }


        Column(modifier = Modifier.padding(innerPadding)) {
            SearchBar(query = searchQuery, onQueryChange = { searchQuery = it })

            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                CityList(cityModels = filteredCityModels)
                if (state.error.isNotBlank()) {
                    Text(
                        text = state.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String, onQueryChange: (String) -> Unit
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text(text = "Search cities...") },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }
    )
}

fun generateRandomColor(): Color {
    val random = Random.Default
    return Color(
        red = random.nextFloat(), green = random.nextFloat(), blue = random.nextFloat(), alpha = 1f
    )
}

@Composable
fun CityItem(cityModel: CityModel) {
    var expanded by remember { mutableStateOf(false) }

    val randomColor = remember { generateRandomColor() }
    Card(
        colors = CardDefaults.cardColors(containerColor = randomColor),
        modifier = Modifier
            .padding(16.dp)
            .clickable { expanded = !expanded }
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth()
            ) {
                Text(
                    text = cityModel.name,
                    style = TextStyle(
                        fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black
                    )
                )
                Text(
                    text = "Population: ${cityModel.population}",
                    style = TextStyle(
                        fontSize = 16.sp, color = Color.Black
                    )
                )
                Text(
                    text = "Area: ${cityModel.area} km²",
                    style = TextStyle(
                        fontSize = 16.sp, color = Color.Black
                    )
                )
                Text(
                    text = "Altitude: ${cityModel.altitude} m",
                    style = TextStyle(
                        fontSize = 16.sp, color = Color.Black
                    )
                )
                Text(
                    text = "Region: ${cityModel.region.tr}",
                    style = TextStyle(
                        fontSize = 16.sp, color = Color.Black
                    )
                )
                Text(
                    text = "Metropolitan: ${if (cityModel.isMetropolitan) "Yes" else "No"}",
                    style = TextStyle(
                        fontSize = 16.sp, color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(randomColor),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Open in Google Maps",
                        style = TextStyle(
                            fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                AnimatedVisibility(visible = expanded) {
                    Column(
                        modifier = Modifier
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .fillMaxWidth()
                    ) {
                        cityModel.districts.map { district ->
                            var expanded by remember { mutableStateOf(false) }
                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                                    .clickable { expanded = !expanded },
                                elevation = CardDefaults.cardElevation(4.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(containerColor = randomColor)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start,
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Place,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.height(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = district.name,
                                        style = TextStyle(
                                            fontSize = 16.sp, color = Color.White
                                        )
                                    )
                                }
                                AnimatedVisibility(visible = expanded) {
                                    Column(
                                        modifier = Modifier.background(
                                            randomColor, shape = RoundedCornerShape(8.dp)
                                        )
                                    ) {
                                        Card(
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .fillMaxWidth()
                                                .clickable { expanded = !expanded },
                                            shape = RoundedCornerShape(8.dp),
                                            colors = CardDefaults.cardColors(containerColor = randomColor)
                                        ) {
                                            Column(
                                                modifier = Modifier.padding(8.dp)
                                            ) {
                                                Row {
                                                    Icon(
                                                        imageVector = Icons.Default.Search,
                                                        contentDescription = null,
                                                        tint = Color.White,
                                                        modifier = Modifier.height(24.dp)
                                                    )
                                                    Text(
                                                        text = " " + district.id.toString(),
                                                        style = TextStyle(
                                                            fontSize = 16.sp, color = Color.White
                                                        )
                                                    )
                                                }
                                                Spacer(modifier = Modifier.height(12.dp))
                                                Row {
                                                    Icon(
                                                        imageVector = Icons.Default.Face,
                                                        contentDescription = null,
                                                        tint = Color.White,
                                                        modifier = Modifier.height(24.dp)
                                                    )
                                                    Text(
                                                        text = " " + district.population.toString(),
                                                        style = TextStyle(
                                                            fontSize = 16.sp, color = Color.White
                                                        )
                                                    )
                                                }
                                                Spacer(modifier = Modifier.height(12.dp))
                                                Row {
                                                    Text(
                                                        text = "Area:",
                                                        style = TextStyle(
                                                            fontSize = 16.sp, color = Color.White
                                                        )
                                                    )
                                                    Text(
                                                        text = " " + district.area.toString() + " km²",
                                                        style = TextStyle(
                                                            fontSize = 16.sp, color = Color.White
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CityList(
    cityModels: List<CityModel>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(cityModels) { city ->
            CityItem(cityModel = city)
        }
    }
}
