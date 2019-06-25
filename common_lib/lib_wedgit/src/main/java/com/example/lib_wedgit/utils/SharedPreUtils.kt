package com.example.lib_wedgit.utils

import android.content.Context
import androidx.annotation.NonNull
import com.alibaba.fastjson.JSON
import com.example.lib_wedgit.utils.encryption.Encode3DES

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-12 : 10:22<br/>
 */
object SharedPreUtils {
    val TAG = this::class.java.simpleName
    private const val SHARED_PATH = "data"


    private val mSharedPreferences = Utils.getApp().getSharedPreferences(SHARED_PATH, Context.MODE_PRIVATE)
    private val editor = mSharedPreferences.edit()


    fun put(key: String, `object`: Any): SharedPreUtils {
        when (`object`) {
            is Set<*> -> editor.putStringSet(key, `object` as Set<String>)
            is String -> editor.putString(key, `object`)
            is Int -> editor.putInt(key, `object`)
            is Boolean -> editor.putBoolean(key, `object`)
            is Float -> editor.putFloat(key, `object`)
            is Long -> editor.putLong(key, `object`)
            else -> editor.putString(key, JSON.toJSONString(`object`))
        }
        editor.apply()
        return this
    }

    /**
     * 获取保存数据的方法，我们根据默认值的到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key_         键的值
     * @param defaultValue 默认值
     * @return
     */
    operator fun get(key_: String, defaultValue: Any): Any? {
        if (!contains(key_)) return defaultValue
        return when (defaultValue) {
            is String -> mSharedPreferences.getString(key_, defaultValue)
            is Int -> mSharedPreferences.getInt(key_, defaultValue)
            is Boolean -> mSharedPreferences.getBoolean(key_, defaultValue)
            is Float -> mSharedPreferences.getFloat(key_, defaultValue)
            is Long -> mSharedPreferences.getLong(key_, defaultValue)
            is Set<*> -> mSharedPreferences.getStringSet(key_, defaultValue as Set<String>)
            else -> mSharedPreferences.getString(key_, null)
        }
    }

    /**
     * SharedPreferences中读取int
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    fun getInt(@NonNull key: String) = getInt(key, -1)

    /**
     * SharedPreferences中读取int
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    fun getInt(@NonNull key: String, defaultValue: Int) = mSharedPreferences.getInt(key, defaultValue)


    /**
     * SharedPreferences中读取long
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    fun getLong(@NonNull key: String) = getLong(key, -1L)


    /**
     * SharedPreferences中读取long
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    fun getLong(@NonNull key: String, defaultValue: Long) = mSharedPreferences.getLong(key, defaultValue)


    /**
     * SharedPreferences中读取float
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    fun getFloat(@NonNull key: String) = getFloat(key, -1f)


    /**
     * SharedPreferences中读取float
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    fun getFloat(@NonNull key: String, defaultValue: Float) = mSharedPreferences.getFloat(key, defaultValue)


    /**
     * SharedPreferences中读取boolean
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值`false`
     */
    fun getBoolean(@NonNull key: String) = getBoolean(key, false)

    /**
     * SharedPreferences中读取boolean
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    fun getBoolean(@NonNull key: String, defaultValue: Boolean) = mSharedPreferences.getBoolean(key, defaultValue)


    /**
     * SharedPreferences中读取StringSet
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值`null`
     */
    fun getStringSet(@NonNull key: String) = getStringSet(key, emptySet())


    /**
     * SharedPreferences中读取StringSet
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    fun getStringSet(@NonNull key: String, @NonNull defaultValue: Set<String>) =
        mSharedPreferences.getStringSet(key, defaultValue)


    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    fun remove(@NonNull key: String) {
        editor.remove(key)
        editor.apply()
    }

    /**
     * 清除所有的数据
     */
    fun clear() {
        editor.clear()
        editor.apply()
    }

    /**
     * 查询某个key是否存在
     *
     * @param key
     * @return
     */
    operator fun contains(key: String): Boolean {
        var isContains = mSharedPreferences.contains(key)
        if (!isContains) isContains = mSharedPreferences.contains(Encode3DES.encode3DES(key))
        if (!isContains) LogUtil.e("你所查询的 $key 值不存在，请检查...")
        return isContains
    }
}