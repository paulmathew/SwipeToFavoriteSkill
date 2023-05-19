package com.example.swipetofavorite.model

data class ResponseRemoveFav(
    val message: String,
    val success: Boolean,
    val code: Int?=null,
    val errors: List<Error>?=null
)