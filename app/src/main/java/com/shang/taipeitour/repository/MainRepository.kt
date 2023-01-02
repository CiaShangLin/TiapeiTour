package com.shang.taipeitour.repository

import com.shang.taipeitour.network.Response
import com.shang.taipeitour.network.TaipeiTourApi
import com.shang.taipeitour.responses.AttractionsResponse

class MainRepository(private val mTaipeiTourApi: TaipeiTourApi) : BaseRepository() {


    suspend fun getAttractionsResponse(lang: String, page: Int): Response<AttractionsResponse> {
        return safeApiCall {
            mTaipeiTourApi.getAttractionsResponse(lang, page)
        }
    }


}