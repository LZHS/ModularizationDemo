package com.example.lib_common

import android.content.Context
import androidx.annotation.NonNull
import com.alibaba.fastjson.JSONException
import com.example.lib_http.entity.BaseResponse
import com.example.lib_http.entity.Errors
import com.example.lib_http.rx_error.ApiException
import com.example.lib_wedgit.utils.LogUtil
import com.github.qingmei2.core.ErrorTransformer
import com.github.qingmei2.retry.RetryConfig
import io.reactivex.Observable
import retrofit2.HttpException

/**
 * Description: RXJava 同意错误拦截以及处理 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-11 : 17:00<br/>
 */
object ErrorProcessor {

    fun <T> processError(@NonNull mContext: Context) = ErrorTransformer<T>(
        //通过onNext流中数据的状态进行操作，多用于与平台自定义错误码
        onNextInterceptor = {
            if (it is BaseResponse<*>) {
                if (Errors.SUCCESS.isSuccess(it.code)) Observable.just(it)
                else Observable.error(ApiException(Errors.values().findLast { err -> err.code == it.code }
                    ?: Errors.UNKNOWN))
            }
            Observable.just(it)
        },
        //通过onError中Throwable状态进行操作
        onErrorResumeNext = { throwable ->
            if (throwable is HttpException) {
                var apiException = when (throwable.code()) {
                    Errors.UNAUTHORIZED.code -> ApiException(Errors.UNAUTHORIZED)
                    Errors.FORBIDDEN.code -> ApiException(Errors.FORBIDDEN)
                    Errors.NOT_FOUND.code -> ApiException(Errors.NOT_FOUND)
                    Errors.REQUEST_TIMEOUT.code -> ApiException(Errors.REQUEST_TIMEOUT)
                    Errors.INTERNAL_SERVER_ERROR.code -> ApiException(Errors.INTERNAL_SERVER_ERROR)
                    Errors.BAD_GATEWAY.code -> ApiException(Errors.BAD_GATEWAY)
                    Errors.SERVICE_UNAVAILABLE.code -> ApiException(Errors.SERVICE_UNAVAILABLE)
                    Errors.GATEWAY_TIMEOUT.code -> ApiException(Errors.GATEWAY_TIMEOUT)
                    else -> ApiException(Errors.UNKNOWN_HTTP)
                }
                Observable.error(apiException)
            } else Observable.error(throwable)

        },
        //错误重试中 添加逻辑
        onErrorRetrySupplier = {
            RetryConfig.none()      // 其它异常都不重试
        },
        //自定义异常
        onErrorConsumer = { throwable ->
            when (throwable) {
                is ApiException -> {
                    mContext.showToast(throwable.msg)
                    LogUtil.d("${throwable.msg} ")
                }
                is JSONException,
                is org.json.JSONException -> {
                    LogUtil.d("Json 解析异常")
                }
            }

        }
    )
}