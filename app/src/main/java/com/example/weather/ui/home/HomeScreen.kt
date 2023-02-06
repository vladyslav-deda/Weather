package com.example.weather.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weather.R
import com.example.weather.util.Resource

@Composable
fun HomeScreen(
  viewModel: HomeViewModel = hiltViewModel()
) {
  val currentWeatherState = viewModel.currentWeatherState.collectAsState()
  val futureWeatherState = viewModel.futureWeatherState.collectAsState()

  val isLoading = currentWeatherState.value is Resource.Loading || futureWeatherState.value is Resource.Loading
  val isSuccess = currentWeatherState.value is Resource.Success && futureWeatherState.value is Resource.Success

  when (isLoading) {
    true -> {
      CircularProgressIndicator(
        modifier = Modifier
          .fillMaxSize()
          .background(Color(0xFF11103A))
          .wrapContentSize(align = Alignment.Center),
        color = colorResource(id = R.color.dark_yellow)
      )
    }
    false -> {
      if (isSuccess) {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF11103A))
        ) {
          TitleWidget()
          CurrentWeatherWidget(
            currentWeatherState.value.data!!
          )
          WeatherForNextDaysWidget()
        }
      } else {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF11103A)),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "You will be able to see the error screen here, but not now:)",
            style = TextStyle(
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 28.sp,
              letterSpacing = 1.sp,
              fontFamily = FontFamily(Font(R.font.fabrik))
            ),
            textAlign = TextAlign.Center
          )
        }

      }
    }
  }
}

@Preview
@Composable
fun PreviewHomeScreen() {
  HomeScreen()
}


