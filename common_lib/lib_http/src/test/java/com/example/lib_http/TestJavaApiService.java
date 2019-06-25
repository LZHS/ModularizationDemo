package com.example.lib_http;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.example.lib_http.TestJavaApiService.TestServiceCont.API_HOST;

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-04 : 13:59<br/>
 */
public interface TestJavaApiService extends TestApi{
    @Override
    default String getApiHost() {
        return API_HOST;
    }

    @GET(TestServiceCont.WEATHER_API)
    Observable<ResponseBody> getWeatherApi(@Query("city") String city);


    interface TestServiceCont {
        String API_HOST = "https://www.apiopen.top/";
        String WEATHER_API = "weatherApi?";
    }
}
