package com.senthilkumaar.dogapp.screen.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.senthilkumaar.dogapp.R
import com.senthilkumaar.dogapp.components.LoadingScreen
import com.senthilkumaar.dogapp.model.Dog
import com.senthilkumaar.dogapp.navigation.Screen
import com.senthilkumaar.dogapp.theme.spacing_m
import com.senthilkumaar.dogapp.theme.spacing_s

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogListScreen(
    navController: NavController,
    viewModel: DogListViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.dog_breeds)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { viewModel.refreshDogs() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = stringResource(R.string.refresh),
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> LoadingScreen(modifier = Modifier.padding(paddingValues))
            uiState.errorMessage != null -> ErrorScreen(
                message = uiState.errorMessage,
                modifier = Modifier.padding(paddingValues),
                onRetry = { viewModel.refreshDogs() }
            )

            else -> DogListContent(
                dogs = uiState.dogs,
                modifier = Modifier.padding(paddingValues),
                onDogClick = { dogId -> navController.navigate(Screen.DogDetail.createRoute(dogId)) }
            )
        }
    }
}

@Composable
fun DogListContent(
    dogs: List<Dog>,
    modifier: Modifier = Modifier,
    onDogClick: (Int) -> Unit
) {
    LazyColumn(modifier = modifier.padding(spacing_s)) {
        items(dogs, key = { it.id }) { dog ->
            DogListItem(dog = dog, onClick = { onDogClick(dog.id) })
        }
    }
}


@Composable
fun ErrorScreen(
    message: String,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = message, color = Color.Red, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(spacing_m))
            Button(onClick = onRetry) {
                Text(text = stringResource(R.string.retry))
            }
        }
    }
}
