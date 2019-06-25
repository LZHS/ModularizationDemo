package com.example.module_login.module

import com.example.lib_wedgit.utils.Utils
import com.example.lib_common.mediator.modules.LoginModule

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-06 : 15:23<br/>
 */
class LoginModuleImp : LoginModule {
    override fun startLoginActivity() {
        Utils.removeAllActivity()
        startLoginActivity()
    }
}