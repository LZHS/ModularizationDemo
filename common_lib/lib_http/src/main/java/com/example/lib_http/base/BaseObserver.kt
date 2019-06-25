package com.example.lib_http.base

import com.example.lib_http.entity.BaseResponse
import com.example.lib_http.rx_error.ApiException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-10 : 17:12<br/>
 */
abstract class BaseObserver <T>:Observer<BaseResponse<T>>{

    override fun onSubscribe(d: Disposable) {
        onRequestStart()
    }

    override fun onNext(baseResponse: BaseResponse<T>) {
        if (baseResponse.isSuccess())
            baseResponse.data?.let { onSuccess(it) }
    }

    override fun onError(e: Throwable) {
        onRequestEnd()

    }

    override fun onComplete() {
        onRequestEnd()
    }

    /**
     * 请求成功，请求体可能为空
     */
    abstract fun onSuccess(response: T?)

    /**
     * 请求失败，如果是约定异常，会提前做统一处理
     */
    abstract fun onFailure(error: ApiException)
    /**
     * 请求开始
     */
     fun onRequestStart(){}

    /**
     * 请求结束
     */
     fun onRequestEnd(){}
}