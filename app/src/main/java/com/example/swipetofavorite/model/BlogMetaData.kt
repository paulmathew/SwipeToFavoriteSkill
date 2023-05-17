package com.example.swipetofavorite.model

data class BlogMetaData(
    val _id: String?=null,
    val blogImageUrl: String?=null,
    val estReadTime: Int?=null,
    val link: String?=null,
    val providerInfo: List<ProviderInfo>,
    val title: String?=null
)