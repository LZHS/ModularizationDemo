package com.example.lib_http.`2019`.`06`.`10`

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-10 : 10:09<br/>
 */
class XiaoHuaProxy constructor(private  val product:IProduct) :IProduct {

    override fun price() {
        println("我是小fff")
        product.price()
        println("现在的价格是300块，我想吃猪肉了")
    }
}