package com.example.lib_http

import com.example.lib_http.help.RetrofitHelp
import com.example.lib_wedgit.utils.SharedPreUtils

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-04 : 10:00<br/>
 */
object ServiceManager {
    val services = HashMap<String, Any?>()
    inline fun <S> getService(serviceClazz: Class<S>): S {
        val serviceName = serviceClazz.name
        var baseUrl = SharedPreUtils.get(serviceName, "").toString()
        if (baseUrl.isEmpty()) throw RuntimeException("请在CustomApplication 中 绑定 ${serviceName}与之对应的Host!!")
        if (!services.containsKey(serviceName)){
            val service=RetrofitHelp.create(serviceClazz, baseUrl)
            service?.let {services.put( serviceName, it )}
        }
        return services[serviceName] as S!!
    }
}