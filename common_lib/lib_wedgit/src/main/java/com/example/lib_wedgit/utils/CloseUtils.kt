package com.example.lib_wedgit.utils

import java.io.Closeable
import java.io.IOException

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-04 : 16:44<br/>
 */
object CloseUtils {

    /**
     * 关闭 IO
     *
     * @param closeables closeables
     */
    fun closeIO( closeables: Array<Closeable>) {
        for (closeable in closeables) {
                try {
                    closeable.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
        }
    }

    /**
     * 安静关闭 IO
     *
     * @param closeables closeables
     */
    fun closeIOQuietly( closeables: Array<Closeable>) {
        for (closeable in closeables) {
                try {
                    closeable.close()
                } catch (ignored: IOException) {
                }
        }
    }
}