package com.example.lib_wedgit.bases.activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lib_wedgit.wedgit.swipe_back.SwipeBackLayout
import com.example.lib_wedgit.wedgit.swipe_back.Utils
import com.example.lib_wedgit.wedgit.swipe_back.app.SwipeBackActivityBase
import com.example.lib_wedgit.wedgit.swipe_back.app.SwipeBackActivityHelper

/**
 * Description: 描述 <br></br>
 * Author: LZHS <br></br>
 * Email: 1050629507@qq.com <br></br>
 * Time: 2018/12/27 : 2:23 PM<br></br>
 */
abstract class BaseSwipeBackActivity : AppCompatActivity(), SwipeBackActivityBase {
    protected val TAG = this.javaClass.name
    protected lateinit var mHelper: SwipeBackActivityHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHelper = SwipeBackActivityHelper(this)
        mHelper.onActivityCreate()
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mHelper.onPostCreate()
    }

    override fun getSwipeBackLayout(): SwipeBackLayout {
        return mHelper.swipeBackLayout
    }

    override fun setSwipeBackEnable(enable: Boolean) {
        swipeBackLayout.setEnableGesture(enable)
    }

    override fun scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this)
        swipeBackLayout.scrollToFinishActivity()
    }
}