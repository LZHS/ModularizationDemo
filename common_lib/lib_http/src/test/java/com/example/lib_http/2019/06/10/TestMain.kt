package com.example.lib_http.`2019`.`06`.`10`

import org.junit.Test

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-10 : 10:11<br/>
 */
class TestMain {

    @Test
    fun runNom(){
        val facialMask =FacialMask()
        val xiaoHuaProxy=XiaoHuaProxy(facialMask)
        xiaoHuaProxy.price()
    }

}