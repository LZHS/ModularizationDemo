package com.example.lib_wedgit.utils.encryption

class Constants {
    /**
     * 密钥 key长度为24个字符，不足可补零
     */
    var secretKey = "1050659507@qq.com.lzhs.lzhs_library"
    /**
     * 向量 向量必须为8位
     */
    var iv = "5l9z2h5s"
    /**
     * 加解密统一使用的编码方式
     */
    var encoding = "UTF-8"
    /**
     * 3des加密
     */
    var algorithm = "desede"
}