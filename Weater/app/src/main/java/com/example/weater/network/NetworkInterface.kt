package com.example.weater.network

import com.example.weater.domain.BaseResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkInterface {
    @GET("{apiKey}/{lat},{lon}")
    fun getInformationAboutLocation(
        @Path("apiKey") apiKey: String,
        @Path("lat") latitude: Double,
        @Path("lon") longitude: Double
    ): Observable<BaseResponse>
}