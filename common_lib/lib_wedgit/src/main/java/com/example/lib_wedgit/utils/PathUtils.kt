package com.example.lib_wedgit.utils

import android.os.Build
import android.os.Environment
import java.io.File

/**
 * Description: 路径相关 <br></br>
 * Author: LZHS <br></br>
 * Email: 1050629507@qq.com <br></br>
 * Time: 2019/3/11 : 3:41 PM<br></br>
 */
object PathUtils {
    /**
     * 获取根路径
     *
     * @return the path of /system
     */
    val rootPath: String
        get() = getAbsolutePath(Environment.getRootDirectory())

    /**
     * 获取数据路径
     *
     * @return the path of /data
     */
    val dataPath: String
        get() = getAbsolutePath(Environment.getDataDirectory())

    /**
     * 获取下载缓存路径
     *
     * @return the path of /cache
     */
    val downloadCachePath: String
        get() = getAbsolutePath(Environment.getDownloadCacheDirectory())

    /**
     * 获取内存应用数据路径
     *
     * @return the path of /data/data/package
     */
    val internalAppDataPath: String
        get() = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Utils.getApp().applicationInfo.dataDir
        } else getAbsolutePath(Utils.getApp().dataDir)

    /**
     * 获取内存应用代码缓存路径
     *
     * @return the path of /data/data/package/code_cache
     */
    val internalAppCodeCacheDir: String
        get() = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Utils.getApp().applicationInfo.dataDir + "/code_cache"
        } else getAbsolutePath(Utils.getApp().codeCacheDir)

    /**
     * 获取内存应用缓存路径
     *
     * @return the path of /data/data/package/cache
     */
    val internalAppCachePath: String
        get() = getAbsolutePath(Utils.getApp().cacheDir)

    /**
     * 获取内存应用数据库路径
     *
     * @return the path of /data/data/package/databases
     */
    val internalAppDbsPath: String
        get() = Utils.getApp().applicationInfo.dataDir + "/databases"

    /**
     * 获取内存应用文件路径
     *
     * @return the path of /data/data/package/files
     */
    val internalAppFilesPath: String
        get() = getAbsolutePath(Utils.getApp().filesDir)

    /**
     * 获取内存应用 SP 路径
     *
     * @return the path of /data/data/package/shared_prefs
     */
    val internalAppSpPath: String
        get() = Utils.getApp().dataDir.absolutePath +"/shared_prefs"

    /**
     * 获取内存应用未备份文件路径
     *
     * @return the path of /data/data/package/no_backup
     */
    val internalAppNoBackupFilesPath: String
        get() = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Utils.getApp().applicationInfo.dataDir + "/no_backup"
        } else getAbsolutePath(Utils.getApp().noBackupFilesDir)

    /**
     * 获取外存路径
     *
     * @return the path of /storage/emulated/0
     */
    val externalStoragePath: String
        get() = if (isExternalStorageDisable) "" else getAbsolutePath(Environment.getExternalStorageDirectory())

    /**
     * 获取外存音乐路径
     *
     * @return the path of /storage/emulated/0/Music
     */
    val externalMusicPath: String
        get() = if (isExternalStorageDisable) "" else getAbsolutePath(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MUSIC
            )
        )

    /**
     * 获取外存播客路径
     *
     * @return the path of /storage/emulated/0/Podcasts
     */
    val externalPodcastsPath: String
        get() = if (isExternalStorageDisable) "" else getAbsolutePath(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PODCASTS
            )
        )

    /**
     * 获取外存铃声路径
     *
     * @return the path of /storage/emulated/0/Ringtones
     */
    val externalRingtonesPath: String
        get() = if (isExternalStorageDisable) "" else getAbsolutePath(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_RINGTONES
            )
        )

    /**
     * 获取外存闹铃路径
     *
     * @return the path of /storage/emulated/0/Alarms
     */
    val externalAlarmsPath: String
        get() = if (isExternalStorageDisable) "" else getAbsolutePath(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_ALARMS
            )
        )

    /**
     * 获取外存通知路径
     *
     * @return the path of /storage/emulated/0/Notifications
     */
    val externalNotificationsPath: String
        get() = if (isExternalStorageDisable) "" else getAbsolutePath(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_NOTIFICATIONS
            )
        )

    /**
     * 获取外存图片路径
     *
     * @return the path of /storage/emulated/0/Pictures
     */
    val externalPicturesPath: String
        get() = if (isExternalStorageDisable) "" else getAbsolutePath(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
            )
        )

    /**
     * 获取外存影片路径
     *
     * @return the path of /storage/emulated/0/Movies
     */
    val externalMoviesPath: String
        get() = if (isExternalStorageDisable) "" else getAbsolutePath(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MOVIES
            )
        )

    /**
     * 获取外存下载路径
     *
     * @return the path of /storage/emulated/0/Download
     */
    val externalDownloadsPath: String
        get() = if (isExternalStorageDisable) "" else getAbsolutePath(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS
            )
        )

    /**
     * 获取外存数码相机图片路径
     *
     * @return the path of /storage/emulated/0/DCIM
     */
    val externalDcimPath: String
        get() = if (isExternalStorageDisable) "" else getAbsolutePath(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM
            )
        )

    /**
     * 获取外存文档路径
     *
     * @return the path of /storage/emulated/0/Documents
     */
    val externalDocumentsPath: String
        get() {
            if (isExternalStorageDisable) return ""
            return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                getAbsolutePath(Environment.getExternalStorageDirectory()) + "/Documents"
            } else getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS))
        }

    /**
     * 获取外存应用数据路径
     *
     * @return the path of /storage/emulated/0/Android/data/package
     */
    val externalAppDataPath: String
        get() {
            if (isExternalStorageDisable) return ""
            val externalCacheDir = Utils.getApp().externalCacheDir ?: return ""
            return getAbsolutePath(externalCacheDir.parentFile)
        }

    /**
     * 获取外存应用缓存路径
     *
     * @return the path of /storage/emulated/0/Android/data/package/cache
     */
    val externalAppCachePath: String
        get() = if (isExternalStorageDisable) "" else getAbsolutePath(Utils.getApp().externalCacheDir)

    /**
     * 获取外存应用文件路径
     *
     * @return the path of /storage/emulated/0/Android/data/package/files
     */
    val externalAppFilesPath: String
        get() = if (isExternalStorageDisable) "" else getAbsolutePath(Utils.getApp().getExternalFilesDir(null))

    /**
     * 获取外存应用音乐路径
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Music
     */
    val externalAppMusicPath: String
        get() = if (isExternalStorageDisable) "" else getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_MUSIC))

    /**
     * 获取外存应用播客路径
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Podcasts
     */
    val externalAppPodcastsPath: String
        get() = if (isExternalStorageDisable) "" else getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_PODCASTS))

    /**
     * 获取外存应用铃声路径
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Ringtones
     */
    val externalAppRingtonesPath: String
        get() = if (isExternalStorageDisable) "" else getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_RINGTONES))

    /**
     * 获取外存应用闹铃路径
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Alarms
     */
    val externalAppAlarmsPath: String
        get() = if (isExternalStorageDisable) "" else getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_ALARMS))

    /**
     * 获取外存应用通知路径
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Notifications
     */
    val externalAppNotificationsPath: String
        get() = if (isExternalStorageDisable) "" else getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS))

    /**
     * 获取外存应用图片路径
     *
     * @return path of /storage/emulated/0/Android/data/package/files/Pictures
     */
    val externalAppPicturesPath: String
        get() = if (isExternalStorageDisable) "" else getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_PICTURES))

    /**
     * 获取外存应用影片路径
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Movies
     */
    val externalAppMoviesPath: String
        get() = if (isExternalStorageDisable) "" else getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_MOVIES))

    /**
     * 获取外存应用下载路径
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Download
     */
    val externalAppDownloadPath: String
        get() = if (isExternalStorageDisable) "" else getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS))

    /**
     * 获取外存应用数码相机图片路径
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/DCIM
     */
    val externalAppDcimPath: String
        get() = if (isExternalStorageDisable) "" else getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_DCIM))

    /**
     * 获取外存应用文档路径
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Documents
     */
    val externalAppDocumentsPath: String
        get() {
            if (isExternalStorageDisable) return ""
            return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                getAbsolutePath(Utils.getApp().getExternalFilesDir(null)) + "/Documents"
            } else getAbsolutePath(
                Utils.getApp().getExternalFilesDir(
                    Environment.DIRECTORY_DOCUMENTS
                )
            )
        }

    /**
     * 获取外存应用 OBB 路径
     *
     * @return the path of /storage/emulated/0/Android/obb/package
     */
    val externalAppObbPath: String
        get() = if (isExternalStorageDisable) "" else getAbsolutePath(Utils.getApp().obbDir)

    private val isExternalStorageDisable: Boolean
        get() = Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()

    /**
     * 获取内存应用数据库路径
     *
     * @param name The name of database.
     * @return the path of /data/data/package/databases/name
     */
    fun getInternalAppDbPath(name: String): String {
        return getAbsolutePath(Utils.getApp().getDatabasePath(name))
    }

    private fun getAbsolutePath(file: File?): String {
        return if (file == null) "" else file.absolutePath
    }
}
