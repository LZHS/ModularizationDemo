package com.lzhs.module_menu.activitys

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_common.base.activity.BaseActivity
import com.example.lib_common.interfaces.OnSelectClickItemListener
import com.example.lib_common.showPopupWindow
import com.lzhs.module_menu.R
import com.lzhs.module_menu.SITEACTIVITY
import com.lzhs.module_menu.SITEACTIVITY_NAME
import com.lzhs.module_menu.startMenuActivity
import kotlinx.android.synthetic.main.activity_site.*

@Route(path = SITEACTIVITY, name = SITEACTIVITY_NAME)
class SiteActivity : BaseActivity() {

    private val testSite = listOf("易流对接测试", "站点测试1", "站点测试2", "站点测试3", "站点测试4", "站点测试5", "站点测试6", "站点测试7", "站点测试8")
    private val testWarehouse = listOf("盐田保税区", "盐田保税区1")
    private val testWork = listOf("A存储区", "B存储区", "C存储区")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site)
        initView()
        initData()
    }

    private fun initData() {
        mViewSelectSite.setOnClickListener {
            this.showPopupWindow(it, OnSelectClickItemListener { position ->
                mTextSiteShow.text = testSite[position]
            }, testSite)
        }
        mViewWarehouse.setOnClickListener {
            this.showPopupWindow(it, OnSelectClickItemListener { position ->
                mTextWarehouseShow.text = testWarehouse[position]
            }, testWarehouse)
        }
        mViewSelectWork.setOnClickListener {
            this.showPopupWindow(it, OnSelectClickItemListener { position ->
                mTextWorkShow.text = testWork[position]
            }, testWork)
        }

        mBtnQuit.setOnClickListener { System.exit(0) }
        mBtnConfirm.setOnClickListener { startMenuActivity() }


    }

    private fun initView() {
        title = "选择站点和仓库"
        getTextLeftBack()?.visibility = View.GONE
        setSwipeBackEnable(false)

    }
}
