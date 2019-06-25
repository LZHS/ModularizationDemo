package com.example.lib_http.help

import com.example.lib_http.BuildConfig
import com.example.lib_http.help.interceptor.getLogInterceptor
import com.example.lib_http.help.interceptor.getNetCacheInterceptor
import com.example.lib_http.help.interceptor.getOfflineCacheInterceptor
import com.example.lib_wedgit.utils.Utils
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-04 : 14:09<br/>
 */
object OkHttpHelp {
    private const val CONNECT_TIME: Long = 15
    private const val CACHE_SIZE: Long = 10 * 1024 * 1024 // 10 MiB

    fun getOkHttpClick(): OkHttpClient =
        OkHttpClient.Builder()
            .setInterceptors()
            .setTime()
            .setSSL()
            .cache(getCache())
            .retryOnConnectionFailure(true)//错误重连
            .build()


    /**
     * 该方法为 绕过证书，添加了一个空主机名验证程序，以使得测试HTTPS 请求能够正常工作，
     * 在实际使用HTTPS的时候请正常解析证书！！！！！
     */
    private fun OkHttpClient.Builder.setSSL() =
        this.sslSocketFactory(createSSLSocketFactory())
            .hostnameVerifier { _, _ -> true }!!

    private fun OkHttpClient.Builder.setInterceptors(): OkHttpClient.Builder {
        if (BuildConfig.DEBUG)
            this.addInterceptor(getNetCacheInterceptor())
                .addInterceptor(getOfflineCacheInterceptor())
                .addInterceptor(getLogInterceptor())
                .addNetworkInterceptor(StethoInterceptor())
        return this

    }


    private fun OkHttpClient.Builder.setTime() =
        this.connectTimeout(CONNECT_TIME, TimeUnit.SECONDS)
            .readTimeout(CONNECT_TIME, TimeUnit.SECONDS)
            .writeTimeout(CONNECT_TIME, TimeUnit.SECONDS)!!

    private fun getCache(): Cache {
        val httpCacheDirectory = File(Utils.getApp().cacheDir, "NetCache")
        return Cache(httpCacheDirectory, CACHE_SIZE)
    }

    private fun createSSLSocketFactory(): SSLSocketFactory? {

        var sSLSocketFactory: SSLSocketFactory? = null

        try {
            val sc = SSLContext.getInstance("TLS")
            sc.init(null, arrayOf<TrustManager>(TrustAllManager()), SecureRandom())
            sSLSocketFactory = sc.socketFactory
        } catch (e: Exception) {
        }

        return sSLSocketFactory
    }

    private class TrustAllManager : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {

        }

        override fun checkServerTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {
        }

        override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate?> {
            return arrayOfNulls(0)
        }


    }
}