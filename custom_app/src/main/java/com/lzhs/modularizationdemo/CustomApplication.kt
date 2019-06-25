package com.lzhs.modularizationdemo

import com.example.lib_common.base.CommonApplication
import com.example.lib_common.mediator.MediatorHelper
import com.example.lib_common.mediator.modules.LoginModule
import com.example.lib_common.mediator.modules.MenuModule
import com.example.lib_wedgit.utils.SharedPreUtils
import com.example.module_login.module.LoginModuleImp
import com.example.module_login.testApi.TestServiceAPI
import com.lzhs.module_menu.module.MenuModuleImp

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-04 : 09:51<br/>
 */
class CustomApplication : CommonApplication() {


    override fun onCreate() {
        super.onCreate()

        initModules()
    }

    /**
     * 初始化各个Module 将要提供的功能
     */
    private fun initModules() {
        // 注册 登录模块需要提供的功能
        MediatorHelper.put(LoginModule::class.java, LoginModuleImp())
        // 注册 功能菜单模块需要提供的功能
        MediatorHelper.put(MenuModule::class.java, MenuModuleImp())

    }

    override fun bindServiceOrHost() {
        SharedPreUtils.put(TestServiceAPI::class.java.name, BuildConfig.API_HOST)

    }


}