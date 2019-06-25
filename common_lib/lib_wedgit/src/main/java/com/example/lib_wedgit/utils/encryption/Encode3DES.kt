package com.example.lib_wedgit.utils.encryption


import java.security.Key
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESedeKeySpec
import javax.crypto.spec.IvParameterSpec

/**
 * Description: 3DES 简单加密 <br></br>
 * 用户可动态设置Constants ，动态配置向量、key 等其他重要参数<br></br>
 * Author: LZHS <br></br>
 * Email: 1050629507@qq.com <br></br>
 * Time: 2018/12/27 : 5:44 PM<br></br>
 */
object Encode3DES {
    private var mConstants: Constants? = null

    /**
     * @param str 需要加密的文字
     * @return 加密后的文字
     * @throws Exception 加密失败
     */
    fun encode3DES(str: String): String {
        try {
            if (mConstants == null) mConstants = Constants()
            var deskey: Key?
            val spec = DESedeKeySpec(mConstants!!.secretKey.toByteArray())
            val keyfactory = SecretKeyFactory
                .getInstance(mConstants!!.algorithm)
            deskey = keyfactory.generateSecret(spec)
            val cipher = Cipher.getInstance("desede/CBC/PKCS5Padding")
            val ips = IvParameterSpec(mConstants!!.iv.toByteArray())
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips)
            val encryptData = cipher.doFinal(str.toByteArray(charset(mConstants!!.encoding)))
            return Base64.encode(encryptData)
        } catch (ex: Exception) {
            ex.printStackTrace()
            return str
        }

    }

    /**
     * 3DES解密
     *
     * @param encryptText 加密文本
     * @return
     * @throws Exception
     */
    fun decode3DES(encryptText: String): String {
        return try {
            if (mConstants == null) mConstants = Constants()
            var deskey: Key?
            val spec = DESedeKeySpec(mConstants!!.secretKey.toByteArray())
            val keyfactory = SecretKeyFactory.getInstance(mConstants!!.algorithm)
            deskey = keyfactory.generateSecret(spec)
            val cipher = Cipher.getInstance("desede/CBC/PKCS5Padding")
            val ips = IvParameterSpec(mConstants!!.iv.toByteArray())
            cipher.init(Cipher.DECRYPT_MODE, deskey, ips)
            val decryptData = cipher.doFinal(Base64.decode(encryptText))
            String(decryptData)
        } catch (ex: Exception) {
            ex.printStackTrace()
            encryptText
        }

    }

    fun setConstants(mConstants: Constants) {
        Encode3DES.mConstants = mConstants
    }
}