package com.senthilkumaar.dogapp.model

data class Dog(
    val id: Int,
    val name: String,
    val temperament: String?,
    val life_span: String?,
    val origin: String?,
    val reference_image_id: String?
) {
    val imageUrl: String
        get() = "https://cdn2.thedogapi.com/images/$reference_image_id.jpg"
}