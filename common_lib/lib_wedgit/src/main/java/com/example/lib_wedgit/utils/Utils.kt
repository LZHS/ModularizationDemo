package com.example.lib_wedgit.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.os.Bundle
import androidx.annotation.NonNull
import com.example.lib_wedgit.utils.permission.PermissionUtils
import java.lang.reflect.InvocationTargetException
import java.util.*


/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-04 : 14:39<br/>
 */
object Utils {
    private var sApplication: Application? = null

    private val ACTIVITY_LIST = LinkedList<Activity>()
    private val mCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
            setTopActivity(activity)
        }

        override fun onActivityStarted(activity: Activity) {
            setTopActivity(activity)
        }

        override fun onActivityResumed(activity: Activity) {
            setTopActivity(activity)
        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            ACTIVITY_LIST.remove(activity)
        }
    }


    fun init(@NonNull context: Context) {
        init(context.applicationContext as Application)
    }


    fun init(@NonNull app: Application) {
        sApplication = app
    }


    fun getApp(): Application {
        if (sApplication == null) throw NullPointerException("请初始化该类")
        sApplication?.registerActivityLifecycleCallbacks(mCallbacks)
        return sApplication as Application
    }


    /**
     * 判断 App 是否处于前台
     *
     * @return
     */
    fun isAppForeground(): Boolean {
        val am = Utils.getApp().getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val info = am.runningAppProcesses
        if (info == null || info.size == 0) return false
        for (aInfo in info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return aInfo.processName == Utils.getApp().packageName
            }
        }
        return false
    }

    fun removeAllActivity() {
        if (!ACTIVITY_LIST.isEmpty()) {
            ACTIVITY_LIST.remove()
        }
    }

    fun getTopActivity(): Activity? {
        if (!ACTIVITY_LIST.isEmpty()) {
            val topActivity = ACTIVITY_LIST.last
            if (topActivity != null) {
                return topActivity
            }
        }
        // using reflect to get top activity
        try {
            @SuppressLint("PrivateApi")
            val activityThreadClass = Class.forName("android.app.ActivityThread")
            val activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null)
            val activitiesField = activityThreadClass.getDeclaredField("mActivities")
            activitiesField.isAccessible = true
            val activities = activitiesField.get(activityThread) as Map<*, *>
            for (activityRecord in activities.values) {
                val activityRecordClass = activityRecord!!::class.java
                val pausedField = activityRecordClass.getDeclaredField("paused")
                pausedField.isAccessible = true
                if (!pausedField.getBoolean(activityRecord)) {
                    val activityField = activityRecordClass.getDeclaredField("activity")
                    activityField.isAccessible = true
                    val activity = activityField.get(activityRecord) as Activity
                    setTopActivity(activity)
                    return activity
                }
            }
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }

        return null
    }

    private fun setTopActivity(activity: Activity) {
        if (activity.javaClass == PermissionUtils.PermissionActivity::class.java) return
        if (ACTIVITY_LIST.contains(activity)) {
            if (ACTIVITY_LIST.last != activity) {
                ACTIVITY_LIST.remove(activity)
                ACTIVITY_LIST.addLast(activity)
            }
        } else {
            ACTIVITY_LIST.addLast(activity)
        }
    }
}