package com.example.lib_wedgit.utils.converter_retrofit

import com.alibaba.fastjson.JSON
import okhttp3.ResponseBody
import retrofit2.Converter

import java.io.IOException
import java.lang.reflect.Type

internal class FastJsonResponseBodyConvert<T>(private val type: Type) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        return JSON.parseObject<T>(value.string(), type)
    }
}