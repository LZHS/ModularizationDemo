package com.lzhs.module_menu.activitys

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_common.base.activity.BaseActivity
import com.lzhs.module_menu.MENUACTIVITY
import com.lzhs.module_menu.MENUACTIVITY_NAME
import com.lzhs.module_menu.R

@Route(path = MENUACTIVITY, name = MENUACTIVITY_NAME)
class MenuActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        initView()
    }

    private fun initView() {
        title = "主菜单"
        setRightText("xxx仓 张三")

    }
}
