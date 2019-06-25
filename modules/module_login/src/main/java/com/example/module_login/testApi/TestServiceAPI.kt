package com.example.module_login.testApi

import com.example.lib_http.entity.BaseResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-05 : 14:37<br/>
 */

interface TestServiceAPI {


    @GET(TestServiceCont.WEATHER_API)
    fun getWeatherApi(@Query("city") city: String = "成都"): Single<BaseResponse<TestDataBean>>


}

interface TestServiceCont {
    companion object {
        const val WEATHER_API = "weatherApi?"
    }
}