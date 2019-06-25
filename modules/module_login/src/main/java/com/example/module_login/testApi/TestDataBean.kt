package com.example.module_login.testApi

/**
 * Description: 描述 <br></br>
 * Author: LZHS <br></br>
 * Email: 1050629507@qq.com <br></br>
 * Time: 2019-06-12 : 17:24<br></br>
 */
class TestDataBean {

    /**
     * yesterday : {"date":"11日星期二","high":"高温 28℃","fx":"无持续风向","low":"低温 20℃","fl":"<![CDATA[<3级]]>","type":"阵雨"}
     * city : 成都
     * aqi : null
     * forecast : [{"date":"12日星期三","high":"高温 30℃","fengli":"<![CDATA[<3级]]>","low":"低温 22℃","fengxiang":"无持续风向","type":"晴"},{"date":"13日星期四","high":"高温 31℃","fengli":"<![CDATA[<3级]]>","low":"低温 22℃","fengxiang":"无持续风向","type":"多云"},{"date":"14日星期五","high":"高温 28℃","fengli":"<![CDATA[<3级]]>","low":"低温 22℃","fengxiang":"无持续风向","type":"阵雨"},{"date":"15日星期六","high":"高温 26℃","fengli":"<![CDATA[<3级]]>","low":"低温 20℃","fengxiang":"无持续风向","type":"小雨"},{"date":"16日星期天","high":"高温 24℃","fengli":"<![CDATA[<3级]]>","low":"低温 21℃","fengxiang":"无持续风向","type":"阵雨"}]
     * ganmao : 各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。
     * wendu : 30
     */

    var yesterday: YesterdayBean? = null
    var city: String? = null
    var aqi: Any? = null
    var ganmao: String? = null
    var wendu: String? = null
    var forecast: List<ForecastBean>? = null

    class YesterdayBean {
        /**
         * date : 11日星期二
         * high : 高温 28℃
         * fx : 无持续风向
         * low : 低温 20℃
         * fl : <![CDATA[<3级]]>
         * type : 阵雨
         */

        var date: String? = null
        var high: String? = null
        var fx: String? = null
        var low: String? = null
        var fl: String? = null
        var type: String? = null
    }

    class ForecastBean {
        /**
         * date : 12日星期三
         * high : 高温 30℃
         * fengli : <![CDATA[<3级]]>
         * low : 低温 22℃
         * fengxiang : 无持续风向
         * type : 晴
         */

        var date: String? = null
        var high: String? = null
        var fengli: String? = null
        var low: String? = null
        var fengxiang: String? = null
        var type: String? = null
    }

    override fun toString(): String {
        return "TestDataBean(yesterday=$yesterday, city=$city, aqi=$aqi, ganmao=$ganmao, wendu=$wendu, forecast=$forecast)"
    }


}
