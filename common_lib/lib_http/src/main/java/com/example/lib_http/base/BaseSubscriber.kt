package com.example.lib_http.base

import com.example.lib_http.entity.BaseResponse
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-11 : 15:20<br/>
 */
abstract class BaseSubscriber <T>: Subscriber<BaseResponse<T>>{

    override fun onSubscribe(s: Subscription?) {
        onRequestStart()
    }

    override fun onNext(t: BaseResponse<T>?) {

    }

    override fun onError(t: Throwable?) {

    }

    override fun onComplete() {

    }

    /**
     * 请求成功，请求体可能为空
     */
    abstract fun onSuccess(response: T?)

    /**
     * 请求失败，如果是约定异常，会提前做统一处理
     */
    //abstract fun onFailure(error: ApiException)
    /**
     * 请求开始
     */
    protected fun onRequestStart(){}

    /**
     * 请求结束
     */
    protected fun onRequestEnd(){}
}