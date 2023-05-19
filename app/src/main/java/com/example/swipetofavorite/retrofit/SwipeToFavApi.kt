package com.example.swipetofavorite.retrofit

import com.example.swipetofavorite.model.AddToFavSkill
import com.example.swipetofavorite.model.RemoveFavoriteSkill
import com.example.swipetofavorite.model.ResponseAddToFav
import com.example.swipetofavorite.model.ResponseRemoveFav
import com.example.swipetofavorite.model.SkillList
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface SwipeToFavApi {

    @GET("v0.8/discover/topicDetails/physical%20fitness")
    fun getPhysicalFitnessTopicDetails(    @HeaderMap headers: Map<String, String>
    ): Observable<SkillList>

    @POST("v0.8/favorite/remove")
    fun removeFavoriteSkill(@Body removeFavoriteSkill: RemoveFavoriteSkill,@HeaderMap headers: Map<String, String>):Observable<ResponseRemoveFav>

    @POST("v0.8/favorite/add")
    fun addToFavoriteSkill(@Body addToFavSkill: AddToFavSkill,@HeaderMap headers: Map<String, String>):Observable<ResponseAddToFav>
}