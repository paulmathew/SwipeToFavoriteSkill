package com.example.swipetofavorite.model

import com.example.swipetofavorite.utils.FavoriteFlag
import com.example.swipetofavorite.utils.getDateTime

data class Skill(
    val availability: Availability?=null,
    val blogMetaData: BlogMetaData?=null,
    val columns: Int?=null,
    val dictionaryName: String?=null,
    val displayTileName: String?=null,
    var isFavorite: Boolean?=null,
    val moreProvidersAvailable: Boolean?=null,
    val providerInfo: MutableList<ProviderInfo>,
    val skillIcon: String?=null,
    val tileBackground: String?=null,
    val tileColor: String?=null,
    val tileName: String?=null,
    val type: String?=null,
    var isSwiped:Boolean=false,
    var currentStateFlag:FavoriteFlag?=null
)
{
    var visibleName:String
        get() {
            return if(displayTileName.isNullOrEmpty())
                tileName.toString()
            else
                displayTileName.toString()
        }
        set(_) {}

    var timeRange:String
        get() {
            return if((availability?.startTime != null) && (availability.endTime != null))
                "${getDateTime(availability.startTime.toString())} - ${getDateTime(availability.endTime.toString())}"
            else
                ""

        }
        set(_) {}

    var isTime:Boolean
        get() {
            return !timeRange.isNullOrEmpty()
        }
        set(_) {}
}