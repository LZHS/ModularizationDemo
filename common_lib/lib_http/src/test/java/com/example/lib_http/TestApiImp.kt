package com.example.lib_http

import com.example.lib_http.TestServiceCont.Companion.API_HOST
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Description: 描述 <br></br>
 * Author: LZHS <br></br>
 * Email: 1050629507@qq.com <br></br>
 * Time: 2019-06-10 : 14:59<br></br>
 */
interface TestApiImp : TestApi {
    override fun getApiHost(): String {
        return API_HOST
    }

    @GET(TestServiceCont.WEATHER_API)
    fun getWeatherApi(@Query("city") city: String): Observable<ResponseBody>


    interface TestServiceCont {
        companion object {
            const val API_HOST = "https://www.apiopen.top/"
            const val WEATHER_API = "weatherApi?"
        }
    }
}