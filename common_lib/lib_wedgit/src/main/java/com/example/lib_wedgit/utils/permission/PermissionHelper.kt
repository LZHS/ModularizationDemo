package com.example.lib_wedgit.utils.permission

import androidx.appcompat.app.AlertDialog
import com.example.lib_wedgit.R
import com.example.lib_wedgit.utils.Utils
import com.example.lib_wedgit.utils.permission.constants.PermissionConstants


/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-04 : 14:40<br/>
 */
object PermissionHelper {
    /**
     * 允许程序获取用户的日历数据<br></br><br></br>
     * android.permission.READ_CALENDAR  允许程序读取用户的日程信息<br></br>
     * android.permission.WRITE_CALENDAR  允许程序写入用户的日程信息<br></br>
     *
     * @param listener
     */
    fun requestCalendar(listener: OnPermissionGrantedListener) {
        request(listener, PermissionConstants.CALENDAR)
    }


    /**
     * 允许程序获取用户的日历数据<br></br><br></br>
     * android.permission.READ_CALENDAR  允许程序读取用户的日程信息<br></br>
     * android.permission.WRITE_CALENDAR  允许程序写入用户的日程信息<br></br>
     *
     * @param listener
     * @param deniedListener
     */
    fun requestCalendar(listener: OnPermissionGrantedListener, deniedListener: OnPermissionDeniedListener) {
        request(listener, deniedListener, PermissionConstants.CALENDAR)
    }

    /**
     * 允许程序访问摄像头进行拍照<br></br>
     * android.permission.CAMERA
     *
     * @param listener
     */
    fun requestCamera(listener: OnPermissionGrantedListener) {
        request(listener, PermissionConstants.CAMERA)
    }

    /**
     * 允许程序访问摄像头进行拍照<br></br>
     * android.permission.CAMERA
     *
     * @param listener
     * @param deniedListener
     */
    fun requestCamera(listener: OnPermissionGrantedListener, deniedListener: OnPermissionDeniedListener) {
        request(listener, deniedListener, PermissionConstants.CAMERA)
    }

    /**
     * 允许程序读取联系<br></br>
     * android.permission.READ_CONTACTS  允许应用访问联系人通讯录信息<br></br>
     * android.permission.WRITE_CONTACTS  允许应用写入联系人通讯录信息<br></br>
     * android.permission.GET_ACCOUNTS  允许应用访问GMail账户列表<br></br>
     *
     * @param listener
     */
    fun requestContacts(listener: OnPermissionGrantedListener) {
        request(listener, PermissionConstants.CONTACTS)
    }

    /**
     * 允许程序读取联系<br></br>
     * android.permission.READ_CONTACTS  允许应用访问联系人通讯录信息<br></br>
     * android.permission.WRITE_CONTACTS  允许应用写入联系人通讯录信息<br></br>
     * android.permission.GET_ACCOUNTS  允许应用访问GMail账户列表<br></br>
     *
     * @param listener
     * @param deniedListener
     */
    fun requestContacts(listener: OnPermissionGrantedListener, deniedListener: OnPermissionDeniedListener) {
        request(listener, deniedListener, PermissionConstants.CONTACTS)
    }

    /**
     * 允许程序访问位置<br></br>
     * android.permission.ACCESS_FINE_LOCATION  通过GPS芯片接收卫星的定位信息 定位精度达10米以内  <br></br>
     * android.permission.ACCESS_COARSE_LOCATION  获得 粗略位置信息数据  <br></br>
     *
     * @param listener
     */
    fun requestLocation(listener: OnPermissionGrantedListener) {
        request(listener, PermissionConstants.LOCATION)
    }

    /**
     * 允许程序访问位置<br></br>
     * android.permission.ACCESS_FINE_LOCATION  通过GPS芯片接收卫星的定位信息 定位精度达10米以内  <br></br>
     * android.permission.ACCESS_COARSE_LOCATION  获得 粗略位置信息数据  <br></br>
     *
     * @param listener
     * @param deniedListener
     */
    fun requestLocation(listener: OnPermissionGrantedListener, deniedListener: OnPermissionDeniedListener) {
        request(listener, deniedListener, PermissionConstants.LOCATION)
    }

    /**
     * 允许程序访问麦克风<br></br>
     * android.permission.RECORD_AUDIO    允许一个应用程序录音.<br></br>
     *
     * @param listener
     */
    fun requestMicrophone(listener: OnPermissionGrantedListener) {
        request(listener, PermissionConstants.MICROPHONE)
    }

    /**
     * 允许程序访问麦克风<br></br>
     * android.permission.RECORD_AUDIO    允许一个应用程序录音.<br></br>
     *
     * @param listener
     * @param deniedListener
     */
    fun requestMicrophone(listener: OnPermissionGrantedListener, deniedListener: OnPermissionDeniedListener) {
        request(listener, deniedListener, PermissionConstants.MICROPHONE)
    }

    /**
     * 允许程序访问电话信息权限组
     *
     * @param listener
     */
    fun requestPhone(listener: OnPermissionGrantedListener) {
        request(listener, PermissionConstants.PHONE)
    }

    /**
     * 允许程序访问电话信息权限组
     *
     * @param grantedListener
     * @param deniedListener
     */
    fun requestPhone(
        grantedListener: OnPermissionGrantedListener,
        deniedListener: OnPermissionDeniedListener
    ) {
        request(grantedListener, deniedListener, PermissionConstants.PHONE)
    }


    /**
     * 允许程序访问手机传感器<br></br>
     * android.permission.BODY_SENSORS  <br></br>
     *
     * @param listener
     */
    fun requestSensors(listener: OnPermissionGrantedListener) {
        request(listener, PermissionConstants.SENSORS)
    }

    /**
     * 允许程序访问手机传感器<br></br>
     * android.permission.BODY_SENSORS  <br></br>
     *
     * @param listener
     * @param deniedListener
     */
    fun requestSensors(listener: OnPermissionGrantedListener, deniedListener: OnPermissionDeniedListener) {
        request(listener, deniedListener, PermissionConstants.SENSORS)
    }

    /**
     * 允许程序访问手机短信<br></br>
     * android.permission.SEND_SMS   发送短信<br></br>
     * android.permission.RECEIVE_SMS    接收短信<br></br>
     * android.permission.READ_SMS    读取短信内容<br></br>
     * android.permission.RECEIVE_WAP_PUSH    接收WAP PUSH信息<br></br>
     * android.permission.RECEIVE_MMS   接收彩信<br></br>
     * android.permission.READ_CELL_BROADCASTS   获取小区广播 <br></br>
     *
     * @param listener
     */
    fun requestSms(listener: OnPermissionGrantedListener) {
        request(listener, PermissionConstants.SMS)
    }

    /**
     * 允许程序访问手机短信<br></br>
     * android.permission.SEND_SMS   发送短信<br></br>
     * android.permission.RECEIVE_SMS    接收短信<br></br>
     * android.permission.READ_SMS    读取短信内容<br></br>
     * android.permission.RECEIVE_WAP_PUSH    接收WAP PUSH信息<br></br>
     * android.permission.RECEIVE_MMS   接收彩信<br></br>
     * android.permission.READ_CELL_BROADCASTS   获取小区广播 <br></br>
     *
     * @param listener
     * @param deniedListener
     */
    fun requestSms(listener: OnPermissionGrantedListener, deniedListener: OnPermissionDeniedListener) {
        request(listener, deniedListener, PermissionConstants.SMS)
    }

    /**
     * 允许程序访问存储<br></br>
     * android.permission.READ_EXTERNAL_STORAGE   读取内存卡<br></br>
     * android.permission.WRITE_EXTERNAL_STORAGE   写内存卡 <br></br>
     *
     * @param listener
     */
    fun requestStorage(listener: OnPermissionGrantedListener) {
        request(listener, PermissionConstants.STORAGE)
    }

    /**
     * 允许程序访问存储<br></br>
     * android.permission.READ_EXTERNAL_STORAGE   读取内存卡<br></br>
     * android.permission.WRITE_EXTERNAL_STORAGE   写内存卡 <br></br>
     *
     * @param listener
     * @param deniedListener
     */
    fun requestStorage(listener: OnPermissionGrantedListener, deniedListener: OnPermissionDeniedListener) {
        request(listener, deniedListener, PermissionConstants.STORAGE)
    }


    private fun request(
        grantedListener: OnPermissionGrantedListener,
        @PermissionConstants.Permission vararg permissions: String
    ) {
        request(grantedListener, null, *permissions)
    }

    private fun request(
        grantedListener: OnPermissionGrantedListener?,
        deniedListener: OnPermissionDeniedListener?,
        @PermissionConstants.Permission vararg permissions: String
    ) {
        PermissionUtils.sInstance.permission(*permissions)
            .rationale(object :PermissionUtils.OnRationaleListener{
                override fun rationale(shouldRequest: PermissionUtils.OnRationaleListener.ShouldRequest) {
                    showRationaleDialog(shouldRequest)
                }
            })
            .callback(object : PermissionUtils.FullCallback {

                override fun onGranted(permissionsGranted: List<String>) {
                    grantedListener?.onPermissionGranted()
                    //LogUtil.d(permissionsGranted)
                }

                override fun onDenied(permissionsDeniedForever: List<String>?, permissionsDenied: List<String>) {
                    if (permissionsDeniedForever!!.isNotEmpty())
                        showOpenAppSettingDialog()
                    deniedListener?.onPermissionDenied()
                    //LogUtil.d(permissionsDeniedForever, permissionsDenied)
                }
            })
            .request()
    }

    fun showRationaleDialog(shouldRequest: PermissionUtils.OnRationaleListener.ShouldRequest) {
        val topActivity = Utils.getTopActivity() ?: return
        AlertDialog.Builder(topActivity)
            .setTitle(R.string.dialog_alert_title)
            .setMessage(R.string.permission_rationale_message)
            .setPositiveButton(android.R.string.ok) { _, _ -> shouldRequest.again(true) }
            .setNegativeButton(android.R.string.cancel) { _, _ -> shouldRequest.again(false) }
            .setCancelable(false)
            .create()
            .show()

    }

    fun showOpenAppSettingDialog() {
        val topActivity = Utils.getTopActivity() ?: return
        AlertDialog.Builder(topActivity)
            .setTitle(android.R.string.dialog_alert_title)
            .setMessage(R.string.permission_denied_forever_message)
            .setPositiveButton(android.R.string.ok) { _, _ -> PermissionUtils.sInstance.launchAppDetailsSettings() }
            .setNegativeButton(android.R.string.cancel) { _, _ ->

            }
            .setCancelable(false)
            .create()
            .show()
    }

    interface OnPermissionGrantedListener {
        fun onPermissionGranted()
    }

    interface OnPermissionDeniedListener {
        fun onPermissionDenied()
    }
}