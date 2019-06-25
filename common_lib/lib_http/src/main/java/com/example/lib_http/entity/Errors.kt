package com.example.lib_http.entity

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-11 : 17:21<br/>
 */
enum class Errors(var code: Int, var msg: String) {

    UNKNOWN(-1, "未知错误"),
    SUCCESS(0, ""),

    //region 常见HTTP 状态码
    OK(200, ""),
    UNAUTHORIZED(401, "未通过服务器未验证"),
    FORBIDDEN(403, "服务器禁止访问"),
    NOT_FOUND(404, "服务器不存在"),
    REQUEST_TIMEOUT(408, "服务器请求超时"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    BAD_GATEWAY(502, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务器不能处理请求"),
    GATEWAY_TIMEOUT(504, "服务器网关超时"),
    UNKNOWN_HTTP(-200, "网络错误");
    //endregion

    //region login 模块错误状态码 1000~1500

    //endregion
    fun isSuccess(code: Int) = Errors.SUCCESS.code == code

    fun findByCode(code: Int) = values().findLast { it.code == code }

}