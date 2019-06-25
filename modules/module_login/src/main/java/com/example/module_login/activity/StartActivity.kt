package com.example.module_login.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_common.base.activity.BaseActivity
import com.example.lib_common.base.fragment.BaseFragment
import com.example.lib_common.utils.StatusBarUtil
import com.example.module_login.R
import com.example.module_login.STARTACTIVITY
import com.example.module_login.STARTACTIVITY_NAME
import com.example.module_login.fragmens.start.StyleOneFragment
import com.example.module_login.startLoginActivity

/**
 * Description: 整个应用启动的第一个页面，
 * 该页面可作为启动页面（闪屏页面） <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-05 : 14:37<br/>
 */
@Route(path = STARTACTIVITY, name = STARTACTIVITY_NAME)
class StartActivity : BaseActivity() {


    val fragments = ArrayList<BaseFragment>()
    var activltyFlag = STYLE_FLAG.ONE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        StatusBarUtil.setTranslucent(this, 0)
        initView()
    }

    private fun initView() {
        setSwipeBackEnable(false)

        fragments.clear()
        when (activltyFlag) {
            STYLE_FLAG.ONE -> {
                val styleOneFragment = StyleOneFragment()
                styleOneFragment.mStartActivity = this
                fragments.add(styleOneFragment)
            }
            STYLE_FLAG.TOW -> {
            }
            else -> fragments.add(StyleOneFragment())
        }
        supportFragmentManager.beginTransaction().let { transaction ->
            fragments.forEach { fragment -> transaction.add(R.id.container, fragment) }
            transaction.commit()
        }


    }

    fun nextActivity() {
        startLoginActivity()
        this@StartActivity.finish()

    }

    enum class STYLE_FLAG {
        ONE, TOW;
    }

}
