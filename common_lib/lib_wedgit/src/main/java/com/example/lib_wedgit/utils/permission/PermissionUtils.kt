package com.example.lib_wedgit.utils.permission

import android.app.Activity
import android.content.Context
import androidx.core.app.AppLaunchChecker.onActivityCreate
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import android.os.Build
import android.content.pm.PackageManager
import android.net.Uri
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.lib_wedgit.utils.Utils
import com.example.lib_wedgit.utils.permission.constants.PermissionConstants
import java.util.*
import java.util.Collections.emptyList
import java.util.Arrays.asList
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashSet


/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-04 : 14:41<br/>
 */
class PermissionUtils private constructor(){
    companion object{
     var sInstance =SingletonHolder.holder
    }
    private object  SingletonHolder{
        val holder=PermissionUtils()
    }

    private val PERMISSIONS = getPermissions()


    private var mOnRationaleListener: OnRationaleListener? = null
    private var mSimpleCallback: SimpleCallback? = null
    private var mFullCallback: FullCallback? = null
    private var mThemeCallback: ThemeCallback? = null
    private lateinit var mPermissions: MutableSet<String>
    private var mPermissionsRequest: MutableList<String>? = null
    private var mPermissionsGranted: MutableList<String>? = null
    private var mPermissionsDenied: MutableList<String>? = null
    private var mPermissionsDeniedForever: MutableList<String>? = null

    /**
     * 获取应用权限
     *
     * @return 清单文件中的权限列表
     */
    fun getPermissions(): Array<String>? {
        return getPermissions(Utils.getApp().packageName)
    }

    /**
     * 获取应用权限
     *
     * @param packageName 包名
     * @return 清单文件中的权限列表
     */
    fun getPermissions(packageName: String): Array<String>? {
        val pm = Utils.getApp().packageManager
        return try {
            pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
                .requestedPermissions
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            arrayOf()
        }

    }

    /**
     * 是否有未通过权限
     *
     * @param permissions
     * @return
     */
    fun isGranted(vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (!isGranted(permission)) return false
        }
        return true
    }

    private fun isGranted(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(Utils.getApp(), permission)
    }

    /**
     * 跳转至应用权限申请页面
     */
    fun launchAppDetailsSettings() {
        val intent = Intent("android.settings.APPLICATION_DETAILS_SETTINGS")
        intent.data = Uri.parse("package:" + Utils.getApp().packageName)
        Utils.getApp().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    /**
     * 请求动态权限
     *
     * @param permissions
     * @return
     */
    fun permission(@PermissionConstants.Permission vararg permissions: String):PermissionUtils {
        mPermissions = LinkedHashSet()
        for (permission in permissions) {
            for (aPermission in PermissionConstants.getPermissions(permission)) {
                if (PERMISSIONS!!.contains(aPermission)) {
                    mPermissions.add(aPermission)
                }
            }
        }
        sInstance = this
        return sInstance
    }

    private fun PermissionUtils(vararg permissions: String) {
        mPermissions = LinkedHashSet()
        for (permission in permissions) {
            for (aPermission in PermissionConstants.getPermissions(permission)) {
                if (PERMISSIONS!!.contains(aPermission)) {
                    mPermissions.add(aPermission)
                }
            }
        }
        sInstance = this
    }

    /**
     * 也就是设置拒绝权限后再次请求的回调接口
     *
     * @param listener
     * @return
     */
    fun rationale(listener: OnRationaleListener): PermissionUtils {
        mOnRationaleListener = listener
        return this
    }

    /**
     * 设置简单回调
     *
     * @param callback
     * @return
     */
    fun callback(callback: SimpleCallback): PermissionUtils {
        mSimpleCallback = callback
        return this
    }

    /**
     * 设置完全回调
     *
     * @param callback
     * @return
     */
    fun callback(callback: FullCallback): PermissionUtils {
        mFullCallback = callback
        return this
    }

    /**
     * 这个 PermissionActivity 的主题并不一定符合小伙伴们应用的 Activity 相关主题
     * 该方法可设置该页面主题
     *
     * @param callback
     * @return
     */
    fun theme(callback: ThemeCallback): PermissionUtils {
        mThemeCallback = callback
        return this
    }

    /**
     * 开始请求动态权限
     */
    fun request() {
        mPermissionsGranted = ArrayList()
        mPermissionsRequest = ArrayList()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mPermissionsGranted!!.addAll(mPermissions)
            requestCallback()
        } else {
            for (permission in mPermissions) {
                if (isGranted(permission)) {
                    mPermissionsGranted!!.add(permission)
                } else {
                    mPermissionsRequest!!.add(permission)
                }
            }
            if (mPermissionsRequest!!.isEmpty()) {
                requestCallback()
            } else {
                startPermissionActivity()
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun startPermissionActivity() {
        mPermissionsDenied = ArrayList()
        mPermissionsDeniedForever = ArrayList()

        val starter = Intent(Utils.getApp(), PermissionActivity::class.java)
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        Utils.getApp().startActivity(starter)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun rationale(activity: Activity): Boolean {
        var isRationale = false
        if (mOnRationaleListener != null) {
            for (permission in mPermissionsRequest!!) {
                if (activity.shouldShowRequestPermissionRationale(permission)) {
                    getPermissionsStatus(activity)
                    mOnRationaleListener!!.rationale(object : OnRationaleListener.ShouldRequest {
                        override fun again(again: Boolean) {
                            if (again) {
                                startPermissionActivity()
                            } else {
                                requestCallback()
                            }
                        }
                    })
                    isRationale = true
                    break
                }
            }
            mOnRationaleListener = null
        }
        return isRationale
    }

    private fun getPermissionsStatus(activity: Activity) {
        for (permission in mPermissionsRequest!!) {
            if (isGranted(permission)) {
                mPermissionsGranted!!.add(permission)
            } else {
                mPermissionsDenied!!.add(permission)
                if (!activity.shouldShowRequestPermissionRationale(permission)) {
                    mPermissionsDeniedForever!!.add(permission)
                }
            }
        }
    }

    private fun requestCallback() {
        if (mSimpleCallback != null) {
            if (mPermissionsRequest!!.size == 0 || mPermissions.size == mPermissionsGranted!!.size) {
                mSimpleCallback!!.onGranted()
            } else {
                if (!mPermissionsDenied!!.isEmpty()) {
                    mSimpleCallback!!.onDenied()
                }
            }
            mSimpleCallback = null
        }
        if (mFullCallback != null) {
            if (mPermissionsRequest!!.size == 0 || mPermissions.size == mPermissionsGranted!!.size) {
                mPermissionsGranted?.let { mFullCallback!!.onGranted(it) }
            } else {
                if (!mPermissionsDenied!!.isEmpty()) {
                    mFullCallback!!.onDenied(mPermissionsDeniedForever, mPermissionsDenied!!)
                }
            }
            mFullCallback = null
        }
        mOnRationaleListener = null
        mThemeCallback = null
    }

    private fun onRequestPermissionsResult(activity: Activity) {
        getPermissionsStatus(activity)
        requestCallback()
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    class PermissionActivity : Activity() {
        override fun onCreate(@Nullable savedInstanceState: Bundle?) {
            if (sInstance.mThemeCallback != null) {
                sInstance.mThemeCallback!!.onActivityCreate(this)
            }
            super.onCreate(savedInstanceState)

            if (sInstance.rationale(this)) {
                finish()
                return
            }
            if (sInstance.mPermissionsRequest != null) {
                val size = sInstance.mPermissionsRequest!!.size
                if (size <= 0) {
                    finish()
                    return
                }
                requestPermissions(sInstance.mPermissionsRequest!!.toTypedArray(), 1)
            }
        }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            @NonNull permissions: Array<String>,
            @NonNull grantResults: IntArray
        ) {
            sInstance.onRequestPermissionsResult(this)
            finish()
        }


    }


    interface OnRationaleListener {

        fun rationale(shouldRequest: ShouldRequest)

        interface ShouldRequest {
            fun again(again: Boolean)
        }
    }

    interface SimpleCallback {
        fun onGranted()

        fun onDenied()
    }

    interface FullCallback {
        fun onGranted(permissionsGranted: List<String>)

        fun onDenied(permissionsDeniedForever: List<String>?, permissionsDenied: List<String>)
    }

    interface ThemeCallback {
        fun onActivityCreate(activity: Activity)
    }
}