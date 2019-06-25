package com.example.lib_common

import android.app.Activity
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lib_common.interfaces.OnSelectClickItemListener
import com.example.lib_wedgit.wedgit.recycle_adapter.CommonAdapter
import com.example.lib_wedgit.wedgit.recycle_adapter.base.ViewHolder

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-10 : 17:02<br/>
 */
fun Fragment.showToast(msg: String): Toast {
    val toast = Toast.makeText(this.context, msg, Toast.LENGTH_LONG)
    toast.show()
    return toast
}

fun Context.showToast(msg: String): Toast {
    val toast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
    toast.show()
    return toast
}

fun Activity.showToast(msg: String): Toast {
    val toast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
    toast.show()
    return toast
}

fun EditText.setText(msg: String) {
    this.text = Editable.Factory.getInstance().newEditable(msg)
}


fun Activity.showPopupWindow(
    @NonNull parentView: View,
    mOnSelectClickItemListener: OnSelectClickItemListener,
    data: List<String>
) {
    val mPopupRootView = LayoutInflater.from(this).inflate(R.layout.layout_common_popuwindow, null, false)
    val mPopupWindow =
        PopupWindow(mPopupRootView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
    mPopupWindow.apply {
        width = parentView.measuredWidth / 3 * 2
        height = parentView.measuredHeight * (if(data.size>4)4 else data.size)
        animationStyle = R.style.PopupWindowAnimation
        setBackgroundDrawable(BitmapDrawable())
        isOutsideTouchable = true
        isFocusable = true
        showAsDropDown(parentView, parentView.measuredWidth / 3 - 50, 5)
    }
    //region 为PopupWindow 绑定数据
    val mRecycle: RecyclerView = mPopupRootView.findViewById(R.id.mRecycle)
    mRecycle.apply {
        layoutManager = LinearLayoutManager(this@showPopupWindow)
        adapter =
            object :
                CommonAdapter<String>(this@showPopupWindow, R.layout.item_common_popupwindow, data.toMutableList()) {
                override fun convert(holder: ViewHolder?, item: String?, position: Int) {
                    holder?.setText(R.id.mTextShow, item)
                    holder?.convertView?.setOnClickListener {
                        mOnSelectClickItemListener?.onItemClick(position)
                        mPopupWindow.dismiss()
                    }
                }
            }
    }
    //endregion
}

