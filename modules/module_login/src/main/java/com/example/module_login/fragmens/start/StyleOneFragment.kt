package com.example.module_login.fragmens.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lib_common.base.fragment.BaseFragment
import com.example.module_login.R
import com.example.module_login.activity.StartActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-17 : 10:13<br/>
 */
class StyleOneFragment : BaseFragment() {
    lateinit var mRootView: View

    var mStartActivity: StartActivity? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.fragment_style_one, container, false)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        runTime()

    }

    private fun runTime() {
        Observable.intervalRange(1, 3, 1, 1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
            .subscribe {
                if (it == 3L && mStartActivity != null) {
                    mStartActivity?.nextActivity()
                }
            }

    }

}