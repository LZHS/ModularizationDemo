package com.example.lib_http.help

import com.example.lib_wedgit.utils.converter_retrofit.FastJsonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * Description: 描述 <br></br>
 * Author: LZHS <br></br>
 * Email: 1050629507@qq.com <br></br>
 * Time: 2019-06-04 : 10:02<br></br>
 */
object RetrofitHelp {

    private val retrofitBuilder = Retrofit.Builder()
        .client(OkHttpHelp.getOkHttpClick())
        .addConverterFactory(FastJsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    fun <S> create(service: Class<S>, baseUrl: String): S =
        retrofitBuilder.baseUrl(baseUrl)
            .build().create(service)


}
