package com.example.swipetofavorite.model

data class Skill(
    val availability: Availability?=null,
    val blogMetaData: BlogMetaData?=null,
    val columns: Int?=null,
    val dictionaryName: String?=null,
    val displayTileName: String?=null,
    val isFavorite: Boolean?=null,
    val moreProvidersAvailable: Boolean?=null,
    val providerInfo: List<ProviderInfoX>,
    val skillIcon: String?=null,
    val tileBackground: String?=null,
    val tileColor: String?=null,
    val tileName: String?=null,
    val type: String?=null
)