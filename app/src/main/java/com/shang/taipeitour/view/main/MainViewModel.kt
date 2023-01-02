package com.shang.taipeitour.view.main

import android.util.Log
import androidx.lifecycle.*
import com.shang.taipeitour.network.ApiState
import com.shang.taipeitour.network.Response
import com.shang.taipeitour.repository.MainRepository
import com.shang.taipeitour.responses.AttractionsResponse
import com.shang.taipeitour.utility.Language
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val mMainRepository: MainRepository) : ViewModel() {

    private var mLanguage = "zh-tw"
    private var mPage = 1
    private val mDataList = mutableListOf<AttractionsResponse.Data?>()

    private val mAttractionsDataFlow = MutableSharedFlow<List<AttractionsResponse.Data?>?>()
    val attractionDataFlow: Flow<List<AttractionsResponse.Data?>?> = mAttractionsDataFlow

    private val mAttractionApiStateLiveData = MutableLiveData<ApiState>()
    val attractionApiStateLiveData: LiveData<ApiState> = mAttractionApiStateLiveData

    init {
        getAttractionsResponse()
    }


    private fun getAttractionsResponse() {
        mAttractionApiStateLiveData.value = ApiState.LOADING
        viewModelScope.launch {
            val response = mMainRepository.getAttractionsResponse(mLanguage, mPage)
            when (response) {
                is Response.Success -> {
                    response.value.data?.let { mDataList.addAll(it) }
                    mAttractionsDataFlow.emit(mDataList.map { it?.copy() })
                    if (response.value.data?.isEmpty() == true && response.value.total == 0) {
                        mAttractionApiStateLiveData.value = ApiState.END
                    } else {
                        mAttractionApiStateLiveData.value = ApiState.CAN_LOADING
                    }
                }
                is Response.Failure -> {
                    mAttractionApiStateLiveData.value = ApiState.ERROR
                }
            }
        }
    }

    fun loadMoreAttractions() {
        if (mAttractionApiStateLiveData.value == ApiState.CAN_LOADING) {
            mPage++
            getAttractionsResponse()
        }
    }

    fun switchLanguage(language: String) {
        if (mLanguage == language) {
            return
        }
        mLanguage = language
        viewModelScope.launch {
            mDataList.clear()
            mPage = 1
            mAttractionsDataFlow.emit(null)
            getAttractionsResponse()
        }

    }
}