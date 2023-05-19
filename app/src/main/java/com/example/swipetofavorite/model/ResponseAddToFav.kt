package com.example.swipetofavorite.model

data class ResponseAddToFav(
    val favorite: Favorite,
    val message: String,
    val showFavoriteToast: Boolean,
    val success: Boolean,
    val code: Int?=null,
    val errors: List<Error>?=null
)