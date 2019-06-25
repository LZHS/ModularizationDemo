package com.example.lib_http

import org.junit.Test


/**
 * Description: 描述 <br></br>
 * Author: LZHS <br></br>
 * Email: 1050629507@qq.com <br></br>
 * Time: 2019-06-04 : 10:26<br></br>
 */
class ServiceManagerTest {

    @Test
    fun getBaseUrl() {

    }


    @Test
    fun getInstance() {
        val clazz = TestServiceAPI::class.java

        println("classLoader = ${clazz.classLoader}")
        println("*********************")
        clazz.fields.forEach(::println)
        println("*********************")
        clazz.methods.forEach(::println)
        println("*********************")
        //println(runMethod(clazz))

    }


    fun runMethod(clazz: Class<TestServiceAPI>): String {
//        val proxy = newProxyInstance(
//            clazz.classLoader,
//            arrayOf(clazz),
//            object : InvocationHandler {
//                override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
//
//
//
//                    return ""
//                }
//            }
//        ) as BaseServiceApi
//        println(proxy.getApiHost())
        return "未找到该方法"
    }


}

