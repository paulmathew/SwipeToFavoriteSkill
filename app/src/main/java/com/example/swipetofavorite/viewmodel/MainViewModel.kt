package com.example.swipetofavorite.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.swipetofavorite.retrofit.SwipeToFavApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel@Inject constructor(
    private val repository: SwipeToFavApi
) : ViewModel() {

    init {
Log.e("ViewModel ","Called")
        repository.getPhysicalFitnessTopicDetails(getHeaderMap())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> Log.e("API CALL response", response.toString()) },
                { error -> Log.e("API CALL error", error.toString()) }
            )
    }


    private fun getHeaderMap(): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjQ3MTYsInVzZXJzIjp7InN0YXR1cyI6MCwidHlwZSI6MCwiaXNNb2JpbGVWZXJpZmllZCI6dHJ1ZX0sImlhdCI6MTY3OTU3MzU4N30.gaiGbeN9tWIojmvSj0imKtCWW0wMhLzN-UjmXevzuyk"
        return headerMap
    }
}