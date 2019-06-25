package com.example.lib_http;

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-10 : 14:55<br/>
 */
public interface TestApi {
    default String getApiHost(){
        return "TestApi - > getApiHost() ";
    }
}
