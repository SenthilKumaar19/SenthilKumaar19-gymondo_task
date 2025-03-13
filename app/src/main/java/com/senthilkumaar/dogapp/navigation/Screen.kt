package com.senthilkumaar.dogapp.navigation


sealed class Screen(val route: String) {
    object DogList : Screen("dog_list")
    object DogDetail : Screen("dog_detail/{dogId}") {
        fun createRoute(dogId: Int) = "dog_detail/$dogId"
    }
}