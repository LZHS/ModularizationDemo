package com.example.lib_wedgit.utils.permission.constants

import android.Manifest
import android.Manifest.permission
import android.annotation.SuppressLint
import androidx.annotation.StringDef


/**
 * android 6.0 之后需要动态申请的权限常量类<br></br>
 * google是以权限组进行分类的，一旦组内的某个权限被赋予了，那么这个组的其他权限也将自动被赋予
 * 作者：LZHS<br></br>
 * 时间： 2017/2/6 17:35<br></br>
 * 邮箱：1050629507@qq.com
 */
@SuppressLint("InlinedApi")
object PermissionConstants {
    /**
     * 允许程序获取用户的日历数据<br></br><br></br>
     * android.permission.READ_CALENDAR  允许程序读取用户的日程信息<br></br>
     * android.permission.WRITE_CALENDAR  允许程序写入用户的日程信息<br></br>
     */
    const val CALENDAR = Manifest.permission_group.CALENDAR
    /**
     * 允许程序访问摄像头进行拍照<br></br>
     * android.permission.CAMERA
     */
    const val CAMERA = Manifest.permission_group.CAMERA
    /**
     * 允许程序读取联系人<br></br><br></br>
     * android.permission.READ_CONTACTS  允许应用访问联系人通讯录信息<br></br>
     * android.permission.WRITE_CONTACTS  允许应用写入联系人通讯录信息<br></br>
     * android.permission.GET_ACCOUNTS  允许应用访问GMail账户列表<br></br>
     */
    const val CONTACTS = Manifest.permission_group.CONTACTS
    /**
     * 允许程序访问位置<br></br>
     * android.permission.ACCESS_FINE_LOCATION  通过GPS芯片接收卫星的定位信息 定位精度达10米以内  <br></br>
     * android.permission.ACCESS_COARSE_LOCATION  获得 粗略位置信息数据  <br></br>
     */
    const val LOCATION = Manifest.permission_group.LOCATION
    /**
     * 允许程序访问麦克风<br></br>
     * android.permission.RECORD_AUDIO    允许一个应用程序录音.<br></br>
     */
    const val MICROPHONE = Manifest.permission_group.MICROPHONE
    /**
     * 允许程序访问电话信息权限组<br></br><br></br>
     * android.permission.READ_PHONE_STATE  访问电话状态<br></br>
     * android.permission.CALL_PHONE  允许程序从非系统拨号器里输入电话号码<br></br>
     * android.permission.READ_CALL_LOG  <br></br>
     * android.permission.WRITE_CALL_LOG  <br></br>
     * com.android.voicemail.permission.ADD_VOICEMAIL  允许应用程序添加系统中的语音邮件<br></br>
     * android.permission.USE_SIP  允许程序使用SIP视频服务<br></br>
     * android.permission.PROCESS_OUTGOING_CALLS  允许应用程序监视、修改、忽略拨出的电话<br></br>
     */
    const val PHONE = Manifest.permission_group.PHONE
    /**
     * 允许程序访问手机传感器<br></br>
     * android.permission.BODY_SENSORS  <br></br>
     */
    const val SENSORS = Manifest.permission_group.SENSORS
    /**
     * 允许程序访问手机短信<br></br>
     * android.permission.SEND_SMS   发送短信<br></br>
     * android.permission.RECEIVE_SMS    接收短信<br></br>
     * android.permission.READ_SMS    读取短信内容<br></br>
     * android.permission.RECEIVE_WAP_PUSH    接收WAP PUSH信息<br></br>
     * android.permission.RECEIVE_MMS   接收彩信<br></br>
     * android.permission.READ_CELL_BROADCASTS   获取小区广播 <br></br>
     */
    const val SMS = Manifest.permission_group.SMS
    /**
     * 允许程序访问存储<br></br><br></br>
     * android.permission.READ_EXTERNAL_STORAGE   读取内存卡<br></br>
     * android.permission.WRITE_EXTERNAL_STORAGE   写内存卡 <br></br>
     */
    const val STORAGE = Manifest.permission_group.STORAGE

    private val GROUP_CALENDAR = arrayOf(permission.READ_CALENDAR, permission.WRITE_CALENDAR)
    private val GROUP_CAMERA = arrayOf(permission.CAMERA)
    private val GROUP_CONTACTS = arrayOf(permission.READ_CONTACTS, permission.WRITE_CONTACTS, permission.GET_ACCOUNTS)
    private val GROUP_LOCATION = arrayOf(permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION)
    private val GROUP_MICROPHONE = arrayOf(permission.RECORD_AUDIO)
    private val GROUP_PHONE = arrayOf(
        permission.READ_PHONE_STATE,
        permission.READ_PHONE_NUMBERS,
        permission.CALL_PHONE,
        permission.ANSWER_PHONE_CALLS,
        permission.READ_CALL_LOG,
        permission.WRITE_CALL_LOG,
        permission.ADD_VOICEMAIL,
        permission.USE_SIP,
        permission.PROCESS_OUTGOING_CALLS
    )
    private val GROUP_SENSORS = arrayOf(permission.BODY_SENSORS)
    private val GROUP_SMS = arrayOf(
        permission.SEND_SMS,
        permission.RECEIVE_SMS,
        permission.READ_SMS,
        permission.RECEIVE_WAP_PUSH,
        permission.RECEIVE_MMS
    )
    private val GROUP_STORAGE = arrayOf(permission.READ_EXTERNAL_STORAGE, permission.WRITE_EXTERNAL_STORAGE)

    @StringDef(CALENDAR, CAMERA, CONTACTS, LOCATION, MICROPHONE, PHONE, SENSORS, SMS, STORAGE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class Permission

    fun getPermissions(@Permission permission: String): Array<String> {
        when (permission) {
            CALENDAR -> return GROUP_CALENDAR
            CAMERA -> return GROUP_CAMERA
            CONTACTS -> return GROUP_CONTACTS
            LOCATION -> return GROUP_LOCATION
            MICROPHONE -> return GROUP_MICROPHONE
            PHONE -> return GROUP_PHONE
            SENSORS -> return GROUP_SENSORS
            SMS -> return GROUP_SMS
            STORAGE -> return GROUP_STORAGE
        }
        return arrayOf(permission)
    }
}
