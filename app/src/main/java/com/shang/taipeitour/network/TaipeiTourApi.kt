package com.shang.taipeitour.network


import com.shang.taipeitour.responses.AttractionsResponse
import retrofit2.http.*

interface TaipeiTourApi {

    @Headers("Accept:application/json")
    @GET("{lang}/Attractions/All?")
    suspend fun getAttractionsResponse(
        @Path("lang") lang: String,
        @Query("page") page: Int
    ): AttractionsResponse

}