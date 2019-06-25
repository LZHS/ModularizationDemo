package com.lzhs.module_menu.module

import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_common.mediator.modules.MenuModule
import com.lzhs.module_menu.SITEACTIVITY

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-17 : 17:26<br/>
 */
class MenuModuleImp : MenuModule {
    override fun startSiteActivity() {
        ARouter.getInstance().build(SITEACTIVITY).navigation()
    }

}