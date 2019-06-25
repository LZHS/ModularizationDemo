package com.example.lib_http.entity

import java.io.Serializable

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-10 : 15:50<br/>
 */
class BaseResponse<T> : Serializable {
    var code: Int = 0
    lateinit var msg: String
    var data: T? = null

    fun isSuccess() = code == 0
}