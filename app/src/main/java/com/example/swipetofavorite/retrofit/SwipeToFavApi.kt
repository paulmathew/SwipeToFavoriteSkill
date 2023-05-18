package com.example.swipetofavorite.retrofit

import com.example.swipetofavorite.model.SkillList
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface SwipeToFavApi {

    @GET("v0.8/discover/topicDetails/physical%20fitness")
    fun getPhysicalFitnessTopicDetails(    @HeaderMap headers: Map<String, String>
    ): Observable<SkillList>
}