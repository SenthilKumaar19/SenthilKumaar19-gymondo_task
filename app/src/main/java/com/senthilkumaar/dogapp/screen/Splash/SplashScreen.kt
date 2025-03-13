package com.senthilkumaar.dogapp.screen.Splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.senthilkumaar.dogapp.R
import com.senthilkumaar.dogapp.navigation.Screen
import com.senthilkumaar.dogapp.theme.spacing_xl
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController){

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.animation_dog)
    )

    var isPlaying by remember { mutableStateOf(true) }

    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying,
        iterations = LottieConstants.IterateForever
    )
    LaunchedEffect(Unit) {
        delay(4000)
        isPlaying = false
        navController.navigate(Screen.DogList.route){
            popUpTo(Screen.DogList.route) { inclusive = true }
        }
    }

    SplashScreenContent(Modifier,composition,progress)
}

@Composable
fun SplashScreenContent(
    modifier: Modifier = Modifier,
    composition: LottieComposition?,
    progress: Float,
){
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
            .padding(top = spacing_xl)
    ){
        val (animationRef, _, _) = createRefs()

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .size(200.dp)
                .constrainAs(animationRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}


@Preview
@Composable
fun SplashScreenPreview(){
    SplashScreenContent(Modifier,null,0f)
}