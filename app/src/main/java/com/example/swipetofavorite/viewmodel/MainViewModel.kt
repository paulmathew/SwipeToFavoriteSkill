package com.example.swipetofavorite.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.swipetofavorite.BuildConfig
import com.example.swipetofavorite.retrofit.SwipeToFavApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel@Inject constructor(
    repository: SwipeToFavApi
) : ViewModel() {

    init {
Log.e("ViewModel ","Called")
        repository.getPhysicalFitnessTopicDetails(getHeaderMap())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> Log.e("API CALL response", response.toString()) },
                { error -> Log.e("API CALL error", error.localizedMessage.toString()) }
            )
    }


    private fun getHeaderMap(): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = BuildConfig.Authorization
        return headerMap
    }
}