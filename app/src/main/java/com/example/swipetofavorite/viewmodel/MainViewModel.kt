package com.example.swipetofavorite.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipetofavorite.BuildConfig
import com.example.swipetofavorite.model.Skill
import com.example.swipetofavorite.model.SkillList
import com.example.swipetofavorite.retrofit.SwipeToFavApi
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel@Inject constructor(
    private val repository: SwipeToFavApi
) : ViewModel() {
    private val _skillList = MutableLiveData<SkillList>() // private member only
    val skillList: LiveData<SkillList> // public and read only.
        get() = _skillList

    val skillListLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    init {
        fetchSkillList()

    }

    fun fetchSkillList(){
        loading.postValue(true)
        viewModelScope.launch {
            repository.getPhysicalFitnessTopicDetails(getHeaderMap())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        run {
                            Log.e("API CALL response", response.toString())
                            loading.postValue(false)
                            _skillList.postValue(response)
                        }
                    },
                    { error ->
                        run {
                            Log.e("API CALL error", error.localizedMessage.toString())
                            loading.postValue(false)
                        }
                    }
                )
        }

    }

    private fun getHeaderMap(): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = BuildConfig.Authorization
        return headerMap
    }
}