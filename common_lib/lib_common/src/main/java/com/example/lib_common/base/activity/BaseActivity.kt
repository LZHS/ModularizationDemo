package com.example.lib_common.base.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import com.example.lib_common.R
import com.example.lib_common.mediator.MediatorHelper
import com.example.lib_common.mediator.modules.MenuModule
import com.example.lib_wedgit.bases.activitys.BaseSwipeBackActivity

/**
 * Description: 基础 Activity <br></br>
 * Author: LZHS <br></br>
 * Email: 1050629507@qq.com <br></br>
 * Time: 2019-06-12 : 15:55<br></br>
 */
open class BaseActivity : BaseSwipeBackActivity() {

    private val titleLength = 8
    var mTooBar: Toolbar? = null

    var mTextTitle: TextView? = null
    var mTextRightShow: TextView? = null
    var mTextLeftBack: TextView? = null
    val mInstance = this

    var onLeftBackOnClickListener = View.OnClickListener { mInstance.finish() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
    }


    override fun setTitle(@NonNull title: CharSequence) {
        mTooBar = findViewById(R.id.mTooBar)
        mTooBar?.let {
            mTextTitle = mTooBar!!.findViewById(R.id.mTextTitle)
            mTextTitle?.apply {
                visibility = View.VISIBLE
                if (title.length < titleLength) text = title
                else text = title.substring(0, titleLength) + "..."
            }
            mTextLeftBack = mTooBar!!.findViewById(R.id.mTextLeftBack)
            mTextLeftBack?.apply {
                visibility = View.VISIBLE
                setOnClickListener(onLeftBackOnClickListener)
            }
        }
    }

    override fun setTitle(@StringRes titleId: Int) {
        title = getString(titleId)
    }

    fun setRightText(textRight: String) {
        mTooBar = findViewById(R.id.mTooBar)
        mTooBar?.let {
            mTextRightShow = mTooBar!!.findViewById(R.id.mTextRightShow)
            mTextRightShow?.apply {
                visibility = View.VISIBLE
                text = textRight
                setOnClickListener { MediatorHelper.get(MenuModule::class.java)?.startSiteActivity() }
            }
        }
    }

    fun getTextRightShow(): TextView? {
        mTooBar = findViewById(R.id.mTooBar)
        mTooBar?.let {
            mTextRightShow = mTooBar!!.findViewById(R.id.mTextRightShow)
        }
        return mTextRightShow
    }

    fun getTextLeftBack(): TextView? {
        mTooBar = findViewById(R.id.mTooBar)
        mTooBar?.let {
            mTextLeftBack = mTooBar!!.findViewById(R.id.mTextLeftBack)
        }
        return mTextLeftBack
    }
}