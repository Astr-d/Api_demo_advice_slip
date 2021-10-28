package com.krillinator.apidemo

import retrofit2.Call
import retrofit2.http.GET

interface AdviceApi {
    @GET("advice")
    fun getInfo(): Call<AdviceSlip>
}