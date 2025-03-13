package com.senthilkumaar.dogapp.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.senthilkumaar.dogapp.R
import com.senthilkumaar.dogapp.components.LoadingScreen
import com.senthilkumaar.dogapp.navigation.Screen
import com.senthilkumaar.dogapp.theme.spacing_m
import com.senthilkumaar.dogapp.theme.spacing_s

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogDetailScreen(
    dogId: Int,
    viewModel: DogDetailViewModel,
    navController: NavController
) {
    val uiState = viewModel.uiState.collectAsState().value
    val dog = uiState.dog
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(dog?.name ?: stringResource(R.string.dog_details)) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screen.DogList.route) {
                            popUpTo(Screen.DogList.route) { inclusive = true }
                        }
                    })
                    {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(spacing_m)
        ) {
            val (imageRef, nameRef, originRef, lifespanRef, temperamentRef, _, errorRef) = createRefs()

            when {
                uiState.isLoading -> {
                    LoadingScreen(modifier = Modifier.padding(paddingValues))
                }

                uiState.errorMessage != null -> {
                    Text(
                        text = uiState.errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.constrainAs(errorRef) {
                            centerTo(parent)
                        }
                    )
                }

                dog != null -> {
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(context)
                                .data(dog.imageUrl)
                                .crossfade(true)
                                .build()
                        ),
                        contentDescription = stringResource(R.string.dog_details),
                        modifier = Modifier
                            .size(250.dp)
                            .clip(RoundedCornerShape(spacing_m))
                            .constrainAs(imageRef) {
                                top.linkTo(parent.top)
                                centerHorizontallyTo(parent)
                            },
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = dog.name,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.constrainAs(nameRef) {
                            top.linkTo(imageRef.bottom, spacing_m)
                            centerHorizontallyTo(parent)
                        }
                    )

                    Text(
                        text = stringResource(
                            R.string.origin,
                            dog.origin ?: stringResource(R.string.unknown)
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.constrainAs(originRef) {
                            top.linkTo(nameRef.bottom, spacing_s)
                            centerHorizontallyTo(parent)
                        }
                    )

                    Text(
                        text = stringResource(
                            R.string.lifespan,
                            dog.life_span ?: stringResource(R.string.unknown)
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.constrainAs(lifespanRef) {
                            top.linkTo(originRef.bottom, spacing_s)
                            centerHorizontallyTo(parent)
                        }
                    )

                    Text(
                        text = stringResource(
                            R.string.temperament,
                            dog.temperament ?: stringResource(R.string.unknown)
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.constrainAs(temperamentRef) {
                            top.linkTo(lifespanRef.bottom, spacing_s)
                            centerHorizontallyTo(parent)
                        }
                    )
                }
            }
        }
    }
}


/*
@Preview(showBackground = true)
@Composable
fun PreviewDogDetail() {
    DogDetailScreen(
        dogId = 1,
        viewModel = FakeDogDetailViewModel(),
        navController = FakeNavController()
    )
}*/
