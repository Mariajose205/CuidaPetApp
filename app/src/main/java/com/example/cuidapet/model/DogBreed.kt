package com.example.cuidapet.model


data class DogBreed(
    val id: Int,
    val name: String,
    val temperament: String?,
    val origin: String?,
    val life_span: String?,
    val image: Image?
)

data class Image(
    val url: String
)
