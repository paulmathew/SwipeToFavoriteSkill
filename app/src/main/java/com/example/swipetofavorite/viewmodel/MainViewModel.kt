package com.example.swipetofavorite.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipetofavorite.BuildConfig
import com.example.swipetofavorite.R
import com.example.swipetofavorite.SwipeToFavApplication
import com.example.swipetofavorite.model.AddToFavSkill
import com.example.swipetofavorite.model.RemoveFavoriteSkill
import com.example.swipetofavorite.model.Skill
import com.example.swipetofavorite.model.SkillList
import com.example.swipetofavorite.retrofit.SwipeToFavApi
import com.example.swipetofavorite.utils.isNetworkConnected
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: SwipeToFavApi
) : ViewModel() {
    private val _skillList = MutableLiveData<SkillList>()
    val skillList: LiveData<SkillList>
        get() = _skillList

    val skillListLoadError = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    //for remove from fav
    val loadingRemoveFav = MutableLiveData<Boolean>()
    val removeFavResponse = MutableLiveData<String>()

    // for add to fav
    val loadingAddFav = MutableLiveData<Boolean>()
    val addToResponse = MutableLiveData<String>()

    init {
        fetchSkillList()

    }

    fun fetchSkillList() {
        loading.postValue(true)
        if (isNetworkConnected)
            viewModelScope.launch {
                repository.getPhysicalFitnessTopicDetails(getHeaderMap())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { response ->
                            run {
                                loading.postValue(false)
                                _skillList.postValue(response)
                            }
                        },
                        { error ->
                            run {
                                loading.postValue(false)
                                skillListLoadError.postValue(error.message)
                            }
                        }
                    )
            }
        else {
            loading.postValue(false)
            skillListLoadError.postValue(SwipeToFavApplication.ctx?.getString(R.string.no_internet_connection))

        }

    }


    fun removeFavoriteSkill(skillName: String) {
        loadingRemoveFav.postValue(true)
        if (isNetworkConnected)
            viewModelScope.launch {
                repository.removeFavoriteSkill(
                    headers = getHeaderMap(),
                    removeFavoriteSkill = RemoveFavoriteSkill(skillName = skillName)
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { response ->

                            run {
                                loadingRemoveFav.postValue(false)

                                if(!response.errors.isNullOrEmpty())
                                {
                                    removeFavResponse.postValue(response.errors.first().message)
                                }
                                else if(response.success){
                                    removeFavResponse.postValue("removed")

                                }
                            }

                        },
                        { error ->
                            run {
                                loadingRemoveFav.postValue(false)

                            }
                        }
                    )
            }
        else {
            loadingRemoveFav.postValue(false)
            skillListLoadError.postValue(SwipeToFavApplication.ctx?.getString(R.string.no_internet_connection))
        }

    }
    fun addToFavoriteSkill(skillName: String,dictionaryName:String) {
        loadingAddFav.postValue(true)
        if (isNetworkConnected)
            viewModelScope.launch {
                repository.addToFavoriteSkill(
                    headers = getHeaderMap(),
                    addToFavSkill = AddToFavSkill(skillName = skillName, dictionaryName = dictionaryName)
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { response ->

                            run {
                                loadingAddFav.postValue(false)

                                if(!response.errors.isNullOrEmpty())
                                {
                                    addToResponse.postValue(response.errors.first().message)
                                }
                                else if(response.success){
                                    addToResponse.postValue("added")

                                }
                            }

                        },
                        { error ->
                            run {
                                loadingAddFav.postValue(false)

                            }
                        }
                    )
            }
        else {
            loadingAddFav.postValue(false)
            skillListLoadError.postValue(SwipeToFavApplication.ctx?.getString(R.string.no_internet_connection))
        }

    }

    private fun getHeaderMap(): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = BuildConfig.Authorization
        return headerMap
    }
}