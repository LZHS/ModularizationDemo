package com.example.lib_wedgit.utils

import android.content.Context
import android.os.Environment
import android.os.storage.StorageManager
import android.os.storage.StorageVolume
import java.lang.reflect.Array
import java.lang.reflect.InvocationTargetException
import java.util.*

internal object SDCardUtils {
    /**
     * 根据 Environment 判断 SD 卡是否可用
     *
     * @return `true`: enabled<br></br>`false`: disabled
     */
    val isSDCardEnableByEnvironment: Boolean
        get() = Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()

    /**
     * 根据 Environment 获取 SD 卡路径
     *
     * @return the path of sdcard by environment
     */
    val sdCardPathByEnvironment: String
        get() = if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            Environment.getExternalStorageDirectory().absolutePath
        } else ""

    /**
     * 获取 SD 卡信息
     *
     * @return the information of sdcard
     */
    val sdCardInfo: List<SDCardInfo>
        get() {
            val paths = ArrayList<SDCardInfo>()
            val sm = Utils.getApp().getSystemService(Context.STORAGE_SERVICE) as StorageManager ?: return paths
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                val storageVolumes = sm.storageVolumes
                try {
                    val getPathMethod = StorageVolume::class.java.getMethod("getPath")
                    for (storageVolume in storageVolumes) {
                        val isRemovable = storageVolume.isRemovable
                        val state = storageVolume.state
                        val path = getPathMethod.invoke(storageVolume) as String
                        paths.add(SDCardInfo(path, state, isRemovable))
                    }
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                }

                return paths

            } else {
                try {
                    val storageVolumeClazz = Class.forName("android.os.storage.StorageVolume")
                    val getPathMethod = storageVolumeClazz.getMethod("getPath")
                    val isRemovableMethod = storageVolumeClazz.getMethod("isRemovable")
                    val getVolumeStateMethod =
                        StorageManager::class.java.getMethod("getVolumeState", String::class.java)
                    val getVolumeListMethod = StorageManager::class.java.getMethod("getVolumeList")
                    val result = getVolumeListMethod.invoke(sm)
                    val length = Array.getLength(result)
                    for (i in 0 until length) {
                        val storageVolumeElement = Array.get(result, i)
                        val path = getPathMethod.invoke(storageVolumeElement) as String
                        val isRemovable = isRemovableMethod.invoke(storageVolumeElement) as Boolean
                        val state = getVolumeStateMethod.invoke(sm, path) as String
                        paths.add(SDCardInfo(path, state, isRemovable))
                    }
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

                return paths
            }
        }

    class SDCardInfo internal constructor(val path: String, val state: String, val isRemovable: Boolean) {

        override fun toString(): String {
            return "SDCardInfo {" +
                    "path = " + path +
                    ", state = " + state +
                    ", isRemovable = " + isRemovable +
                    '}'.toString()
        }
    }
}