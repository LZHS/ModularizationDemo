package com.lzhs.module_menu

import com.alibaba.android.arouter.launcher.ARouter


const val SITEACTIVITY_NAME = "选择站点和仓库页面"
const val SITEACTIVITY = "/menu/SiteActivity"


const val MENUACTIVITY_NAME = "选择站点和仓库页面"
const val MENUACTIVITY = "/menu/MenuActivity"

fun startMenuActivity() {
    ARouter.getInstance().build(MENUACTIVITY).navigation()
}
