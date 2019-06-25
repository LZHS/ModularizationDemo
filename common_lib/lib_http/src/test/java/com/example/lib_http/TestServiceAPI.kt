package com.example.lib_http

import com.example.lib_http.TestServiceCont.Companion.API_HOST
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

open interface TestServiceAPI : TestApi {

    override fun getApiHost(): String {
        return API_HOST
    }


    @GET(TestServiceCont.WEATHER_API)
    fun getWeatherApi(@Query("city") city: String = "成都"): Observable<ResponseBody>
}

interface TestServiceCont {
    companion object {
        const val API_HOST="https://www.apiopen.top/"
        const val WEATHER_API = "weatherApi?"
    }
}