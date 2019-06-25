package com.example.lib_http.help.interceptor

import com.example.lib_wedgit.utils.LogUtil
import com.example.lib_wedgit.utils.NetworkUtils
import okhttp3.FormBody
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


/**
 * Description: 描述 <br></br>
 * Author: LZHS <br></br>
 * Email: 1050629507@qq.com <br></br>
 * Time: 2019/3/1 : 11:36 AM<br></br>
 */

private const val ONLINE_CACHE_TIME = 30//在线的时候的缓存过期时间，如果想要不缓存，直接时间设置为0

/**
 * 有网络的时候缓存请求数据拦截器
 */
fun getNetCacheInterceptor() = Interceptor {
    val request = it.request()
    val response = it.proceed(request)
    return@Interceptor response.newBuilder()
        .header("Cache-Control", "public, max-age=$ONLINE_CACHE_TIME")
        .removeHeader("Pragma")
        .build()
}

/**
 * 无网络的时候读取缓存数据拦截器
 */
fun getOfflineCacheInterceptor() = Interceptor {
    var request = it.request()
    if (!NetworkUtils.isAvailableByPing()) {
        val offlineCacheTime = 60 * 60 * 24 * 7//离线的时候的缓存的过期时间
        request = request.newBuilder()
            .header("Cache-Control", "public, only-if-cached, max-stale=$offlineCacheTime")
            .build()
    }
    return@Interceptor it.proceed(request)
}


fun getLogInterceptor() = object : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val startNs = System.nanoTime()
        val logMsg = StringBuffer("****************** Request ******************")
        val request = chain.request()
        val requestBody = request.body()
        val hasRequestBody = requestBody != null
        val connection = chain.connection()
        logMsg.append("\n* 请求方式 : " + request.method())
            .append("\n* 请求地址 : " + request.url())
            .append("\n* 请求协议 : " + if (connection != null) " " + connection!!.protocol() else "")

        if (hasRequestBody)
            logMsg.append("\n* 请求长度 contentLength : (" + requestBody!!.contentLength() + "-byte body)")
        if (hasRequestBody && requestBody!!.contentType() != null)
            logMsg.append("\n* 请求类型 Content-Type : " + requestBody.contentType()!!)
        logMsg.append("\n* 请求头参数 : " + getHeader(request.headers()))
        if ("POST" == request!!.method() && request.body() is FormBody)
            logMsg.append("\n* 请求参数 FormBody : " + getFormBody((request.body() as FormBody?)!!))
        logMsg.append("\n****************** Response ******************")
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            logMsg.append("\n* HTTP FAILED : $e")
            throw e
        }
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        getResponsBody(logMsg, response)
        logMsg.append("\n* 服务器响应时间 : " + tookMs + "ms")
        LogUtil.d(logMsg.toString())

        return response
    }

    private fun getResponsBody(logMsg: StringBuffer, response: Response) {
        val responseBody = response.peekBody((1024 * 1024).toLong())
        val contentLength = responseBody.contentLength()
        logMsg.append("\n* 请求响应码 : " + response.code())
            .append("\n* 请求响应消息 : " + if (response.message().isEmpty()) "" else response.message())
            .append("\n* 请求体大小 : " + if (contentLength > -1) "$contentLength-byte" else "unknown-length")
            .append("\n* 请求体详细内容 : " + responseBody.string())
    }

    private fun getHeader(headers: Headers): String {
        var headerRes = ""
        if (headers.size() > 0)
            for (i in 0 until headers.size()) {
                if (!"Content-Type".equals(headers.name(i), ignoreCase = true) &&
                    !"Content-Length".equals(headers.name(i), ignoreCase = true)
                )
                    headerRes += "（ " + headers.name(i) + " -- > " + headers.value(i) + " );"
            }
        return headerRes
    }

    private fun getFormBody(body: FormBody): String {
        var bodyRes = ""
        for (i in 0 until body.size())
            bodyRes += " ( " + body.encodedName(i) + " = " + body.encodedValue(i) + " );"
        return bodyRes
    }

}

fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return loggingInterceptor
}
