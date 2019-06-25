package com.example.lib_common.mediator

import com.example.lib_common.mediator.base.BaseModuleApi
import java.util.*

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-05 : 19:14<br/>
 */

object MediatorHelper {

    private val moduleApiMap = HashMap<Class<out BaseModuleApi>, BaseModuleApi>()

    /**
     * 汇总必需要注册的模块列表，如果未注册则抛出异常
     *
     * @param clazz
     * @param <T>
     * @return
    </T> */
    fun <T : BaseModuleApi> get(clazz: Class<T>): T? {
        if (!moduleApiMap.containsKey(clazz))
            throw ClassNotFoundException("${clazz.simpleName}类未注册，请检查代码！！！")
           return moduleApiMap[clazz] as T
    }

    fun putAll(all: Map<Class<out BaseModuleApi>, BaseModuleApi>?) {
        if (all != null) {
            moduleApiMap.putAll(all)
        }
    }

    fun <T: BaseModuleApi>put(clazz: Class<out BaseModuleApi>, module: T) {
        if (module != null && clazz.isInstance(module)) {
            moduleApiMap[clazz] = module
        }
    }

    fun remove(clazz: Class<out BaseModuleApi>) {
        if (moduleApiMap.containsKey(clazz)) {
            moduleApiMap.remove(clazz)
        }
    }
}