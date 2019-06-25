package com.example.lib_http.rx_error

import com.example.lib_http.entity.Errors

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-11 : 21:40<br/>
 */
class ApiException( var code: Int, var msg: String) : Exception(msg) {

    constructor(error: Errors) : this(error.code, error.msg)

}
