package com.example.lib_wedgit.utils

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import androidx.annotation.RequiresPermission
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.net.UnknownHostException

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-04 : 16:39<br/>
 */
object NetworkUtils{
    enum class NetworkType {
        NETWORK_WIFI,
        NETWORK_4G,
        NETWORK_3G,
        NETWORK_2G,
        NETWORK_UNKNOWN,
        NETWORK_NO
    }

    /**
     * 打开网络设置界面
     */
    fun openWirelessSettings() {
        Utils.getApp().startActivity(
            Intent(Settings.ACTION_WIRELESS_SETTINGS)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }

    /**
     * 判断网络是否连接
     *
     * Must hold
     * `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
     *
     * @return `true`: connected<br></br>`false`: disconnected
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    fun isConnected(): Boolean {
        val info = getActiveNetworkInfo()
        return info != null && info.isConnected
    }

    /**
     * 判断网络是否可用
     *
     * Must hold `<uses-permission android:name="android.permission.INTERNET" />`
     *
     * The default ping ip: 223.5.5.5
     *
     * @return `true`: yes<br></br>`false`: no
     */
    @RequiresPermission(INTERNET)
    fun isAvailableByPing(): Boolean {
        return isAvailableByPing(null)
    }

    /**
     * 判断网络是否可用
     *
     * Must hold `<uses-permission android:name="android.permission.INTERNET" />`
     *
     * @param ip The ip address.
     * @return `true`: yes<br></br>`false`: no
     */
    @RequiresPermission(INTERNET)
    fun isAvailableByPing(ip: String?): Boolean {
        // default ping ipd
        val defaultIp= ip ?: "223.5.5.5"
        val result = ShellUtils.execCmd(String.format("ping -c 1 %s", defaultIp), false)
        val ret = result.result === 0
        if (result.errorMsg != null) {
            Log.d("NetworkUtils", "isAvailableByPing() called" + result.errorMsg)
        }
        if (result.successMsg != null) {
            Log.d("NetworkUtils", "isAvailableByPing() called" + result.successMsg)
        }
        return ret
    }

    /**
     * 判断移动数据是否打开
     *
     * @return `true`: enabled<br></br>`false`: disabled
     */
    fun getMobileDataEnabled(): Boolean {
        try {
            val tm = Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager ?: return false
            @SuppressLint("PrivateApi")
            val getMobileDataEnabledMethod = tm.javaClass.getDeclaredMethod("getDataEnabled")
                return getMobileDataEnabledMethod.invoke(tm) as Boolean
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    /**
     * 打开或关闭移动数据
     *
     * Must hold `android:sharedUserId="android.uid.system"`,
     * `<uses-permission android:name="android.permission.MODIFY_PHONE_STATE" />`
     *
     * @param enabled True to enabled, false otherwise.
     */
    @RequiresPermission(MODIFY_PHONE_STATE)
    fun setMobileDataEnabled(enabled: Boolean) {
        try {
            val tm = Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager ?: return
            val setMobileDataEnabledMethod =
                tm.javaClass.getDeclaredMethod("setDataEnabled", Boolean::class.javaPrimitiveType!!)
            setMobileDataEnabledMethod?.invoke(tm, enabled)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 判断网络是否是移动数据
     *
     * Must hold
     * `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
     *
     * @return `true`: yes<br></br>`false`: no
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    fun isMobileData(): Boolean {
        val info = getActiveNetworkInfo()
        return (null != info
                && info.isAvailable
                && info.type == ConnectivityManager.TYPE_MOBILE)
    }

    /**
     * 判断网络是否是 4G
     *
     * Must hold
     * `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
     *
     * @return `true`: yes<br></br>`false`: no
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    fun is4G(): Boolean {
        val info = getActiveNetworkInfo()
        return (info != null
                && info.isAvailable
                && info.subtype == TelephonyManager.NETWORK_TYPE_LTE)
    }

    /**
     * 判断 wifi 是否打开
     *
     * Must hold
     * `<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />`
     *
     * @return `true`: enabled<br></br>`false`: disabled
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    fun getWifiEnabled(): Boolean {
        @SuppressLint("WifiManagerLeak")
        val manager = Utils.getApp().getSystemService(Context.WIFI_SERVICE) as WifiManager
        return manager != null && manager.isWifiEnabled
    }

    /**
     * 打开或关闭 wifi
     *
     * Must hold
     * `<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />`
     *
     * @param enabled True to enabled, false otherwise.
     */
    @SuppressLint("MissingPermission")
    fun setWifiEnabled(enabled: Boolean) {
        @SuppressLint("WifiManagerLeak")
        val manager = Utils.getApp().getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (enabled) {
            if (!manager.isWifiEnabled) {
                manager.isWifiEnabled = true
            }
        } else {
            if (manager.isWifiEnabled) {
                manager.isWifiEnabled = false
            }
        }
    }

    /**
     * 判断 wifi 是否连接状态
     *
     * Must hold
     * `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
     *
     * @return `true`: connected<br></br>`false`: disconnected
     */
    @SuppressLint("MissingPermission")
    fun isWifiConnected(): Boolean {
        val cm = Utils.getApp().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return (cm?.activeNetworkInfo != null
                && cm.activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI)
    }

    /**
     * 判断 wifi 数据是否可用
     *
     * Must hold
     * `<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />`,
     * `<uses-permission android:name="android.permission.INTERNET" />`
     *
     * @return `true`: available<br></br>`false`: unavailable
     */
    @SuppressLint("SupportAnnotationUsage")
    @RequiresPermission(allOf = [ ACCESS_WIFI_STATE, INTERNET ])
    fun isWifiAvailable(): Boolean {
        return getWifiEnabled() && isAvailableByPing()
    }

    /**
     * 获取移动网络运营商名称
     *
     * @return the name of network operate
     */
    fun getNetworkOperatorName(): String {
        val tm = Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return if (tm != null) tm.networkOperatorName else ""
    }

    private val NETWORK_TYPE_GSM = 16
    private val NETWORK_TYPE_TD_SCDMA = 17
    private val NETWORK_TYPE_IWLAN = 18

    /**
     * 获取当前网络类型
     *
     * Must hold
     * `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
     *
     * @return type of network
     *
     *  * [NetworkType.NETWORK_WIFI]
     *  * [NetworkType.NETWORK_4G]
     *  * [NetworkType.NETWORK_3G]
     *  * [NetworkType.NETWORK_2G]
     *  * [NetworkType.NETWORK_UNKNOWN]
     *  * [NetworkType.NETWORK_NO]
     *
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    fun getNetworkType(): NetworkType {
        var netType = NetworkUtils.NetworkType.NETWORK_NO
        val info = getActiveNetworkInfo()
        if (info != null && info.isAvailable) {

            if (info.type == ConnectivityManager.TYPE_WIFI) {
                netType = NetworkUtils.NetworkType.NETWORK_WIFI
            } else if (info.type == ConnectivityManager.TYPE_MOBILE) {
                when (info.subtype) {

                    NETWORK_TYPE_GSM, TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> netType =
                        NetworkUtils.NetworkType.NETWORK_2G

                    NETWORK_TYPE_TD_SCDMA, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP -> netType =
                        NetworkUtils.NetworkType.NETWORK_3G

                    NETWORK_TYPE_IWLAN, TelephonyManager.NETWORK_TYPE_LTE -> netType = NetworkUtils.NetworkType.NETWORK_4G
                    else -> {

                        val subtypeName = info.subtypeName
                        if (subtypeName.equals("TD-SCDMA", ignoreCase = true)
                            || subtypeName.equals("WCDMA", ignoreCase = true)
                            || subtypeName.equals("CDMA2000", ignoreCase = true)
                        ) {
                            netType = NetworkUtils.NetworkType.NETWORK_3G
                        } else {
                            netType = NetworkUtils.NetworkType.NETWORK_UNKNOWN
                        }
                    }
                }
            } else {
                netType = NetworkType.NETWORK_UNKNOWN
            }
        }
        return netType
    }

    @RequiresPermission(ACCESS_NETWORK_STATE)
    private fun getActiveNetworkInfo(): NetworkInfo? {
        val manager =
            Utils.getApp().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return manager.activeNetworkInfo
    }

    /**
     * 获取 IP 地址
     *
     * Must hold `<uses-permission android:name="android.permission.INTERNET" />`
     *
     * @param useIPv4 True to use ipv4, false otherwise.
     * @return the ip address
     */
    @RequiresPermission(INTERNET)
    fun getIPAddress(useIPv4: Boolean): String {
        try {
            val nis = NetworkInterface.getNetworkInterfaces()
            while (nis.hasMoreElements()) {
                val ni = nis.nextElement()
                // To prevent phone of xiaomi return "10.0.2.15"
                if (!ni.isUp) continue
                val addresses = ni.inetAddresses
                while (addresses.hasMoreElements()) {
                    val inetAddress = addresses.nextElement()
                    if (!inetAddress.isLoopbackAddress) {
                        val hostAddress = inetAddress.hostAddress
                        val isIPv4 = hostAddress.indexOf(':') < 0
                        if (useIPv4) {
                            if (isIPv4) return hostAddress
                        } else {
                            if (!isIPv4) {
                                val index = hostAddress.indexOf('%')
                                return if (index < 0)
                                    hostAddress.toUpperCase()
                                else
                                    hostAddress.substring(0, index).toUpperCase()
                            }
                        }
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
        }

        return ""
    }

    /**
     * 获取域名 ip 地址
     *
     * Must hold `<uses-permission android:name="android.permission.INTERNET" />`
     *
     * @param domain The name of domain.
     * @return the domain address
     */
    @RequiresPermission(INTERNET)
    fun getDomainAddress(domain: String): String {
        val inetAddress: InetAddress
        try {
            inetAddress = InetAddress.getByName(domain)
            return inetAddress.hostAddress
        } catch (e: UnknownHostException) {
            e.printStackTrace()
            return ""
        }

    }
}