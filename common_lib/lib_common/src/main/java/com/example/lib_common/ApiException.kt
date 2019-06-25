package com.example.lib_common

import com.example.lib_http.entity.Errors

/**
 * Description: 描述 <br></br>
 * Author: LZHS <br></br>
 * Email: 1050629507@qq.com <br></br>
 * Time: 2019-06-12 : 15:55<br></br>
 */
class ApiException(internal var code: Int, internal var msg: String) : Exception(msg) {

    constructor(error: Errors) : this(error.code, error.msg) {}

}
