package com.example.module_login.activity

import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_common.base.activity.BaseActivity
import com.example.lib_common.interfaces.OnSelectClickItemListener
import com.example.lib_common.mediator.MediatorHelper
import com.example.lib_common.mediator.modules.MenuModule
import com.example.lib_common.showPopupWindow
import com.example.lib_common.utils.StatusBarUtil
import com.example.module_login.LOGINACTIVITY
import com.example.module_login.LOGINACTIVITY_NAME
import com.example.module_login.R
import kotlinx.android.synthetic.main.activity_login.*

@Route(path = LOGINACTIVITY, name = LOGINACTIVITY_NAME)
class LoginActivity : BaseActivity() {


    private val languages = listOf("简体中文","繁體中文", "English")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        StatusBarUtil.setTranslucent(this, 0)
        initView()
        initData()
    }

    private fun initData() {
        mBtnLogin.setOnClickListener {
            MediatorHelper.get(MenuModule::class.java)?.startSiteActivity()
            this@LoginActivity.finish()
            //测试获取网络数据操作
//            ServiceManager.getService(TestServiceAPI::class.java)
//                .getWeatherApi()
//                .compose(singleToUI())
//                .compose(ErrorProcessor.processError(this@LoginActivity))
//                .subscribe(Consumer<BaseResponse<TestDataBean>> {
//                    LogUtil.d("$it ")
//                }, Consumer {
//                    LogUtil.d("$it ")
//                })

        }

        mInputUserName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (it.isNotEmpty()) mImgUserNameClear.visibility = View.VISIBLE
                    else mImgUserNameClear.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        mInputPSD.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (it.isNotEmpty()) mImgUserPSD.visibility = View.VISIBLE
                    else mImgUserPSD.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        mImgUserNameClear.setOnClickListener { mInputUserName.setText("") }
        mImgUserPSD.setOnClickListener { mInputPSD.setText("") }

        mViewSelect.setOnClickListener {
            this@LoginActivity.showPopupWindow( it,
                OnSelectClickItemListener { position ->
                    mTextLanguage.text=languages[position]
                }, languages
            )
        }

    }


    private fun initView() {
        setSwipeBackEnable(false)
        createAnimator()
    }

    private fun createAnimator() {
        ObjectAnimator.ofFloat(mRootView, "alpha", 0f, 1f)
            .setDuration(3000L)
            .apply { interpolator = AccelerateDecelerateInterpolator() }
            .start()
    }
}
