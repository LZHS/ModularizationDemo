package com.example.lib_wedgit.utils

import java.io.*
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.util.*

internal object FileIOUtils {
    private val LINE_SEP = System.getProperty("line.separator")

    private var sBufferSize = 8192

    /**
     * 将输入流写入文件
     *
     * @param filePath The path of file.
     * @param is       The input stream.
     * @return `true`: success<br></br>`false`: fail
     */
    fun writeFileFromIS(filePath: String, `is`: InputStream): Boolean {
        return writeFileFromIS(getFileByPath(filePath), `is`, false)
    }

    /**
     * 将输入流写入文件
     *
     * @param filePath The path of file.
     * @param is       The input stream.
     * @param append   True to append, false otherwise.
     * @return `true`: success<br></br>`false`: fail
     */
    fun writeFileFromIS(
        filePath: String,
        `is`: InputStream,
        append: Boolean
    ): Boolean {
        return writeFileFromIS(getFileByPath(filePath), `is`, append)
    }

    /**
     * 将输入流写入文件
     *
     * @param file   The file.
     * @param is     The input stream.
     * @param append True to append, false otherwise.
     * @return `true`: success<br></br>`false`: fail
     */
    @JvmOverloads
    fun writeFileFromIS(
        file: File?,
        `is`: InputStream,
        append: Boolean = false
    ): Boolean {
        if (!createOrExistsFile(file) || `is` == null) return false
        var os: OutputStream? = null
        try {
            os = BufferedOutputStream(FileOutputStream(file, append))
            val data = ByteArray(sBufferSize)
            var len = 0
            while (({ len = `is`.read(data, 0, sBufferSize);len }()) == -1) {
                os.write(data, 0, len)
            }
            return true
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        } finally {
            try {
                `is`.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            try {
                os?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    /**
     * 将字节数组写入文件
     *
     * @param filePath The path of file.
     * @param bytes    The bytes.
     * @return `true`: success<br></br>`false`: fail
     */
    fun writeFileFromBytesByStream(filePath: String, bytes: ByteArray): Boolean {
        return writeFileFromBytesByStream(getFileByPath(filePath), bytes, false)
    }

    /**
     * 将字节数组写入文件
     *
     * @param filePath The path of file.
     * @param bytes    The bytes.
     * @param append   True to append, false otherwise.
     * @return `true`: success<br></br>`false`: fail
     */
    fun writeFileFromBytesByStream(
        filePath: String,
        bytes: ByteArray,
        append: Boolean
    ): Boolean {
        return writeFileFromBytesByStream(getFileByPath(filePath), bytes, append)
    }

    /**
     * 将字节数组写入文件
     *
     * @param file   The file.
     * @param bytes  The bytes.
     * @param append True to append, false otherwise.
     * @return `true`: success<br></br>`false`: fail
     */
    @JvmOverloads
    fun writeFileFromBytesByStream(
        file: File?,
        bytes: ByteArray?,
        append: Boolean = false
    ): Boolean {
        if (bytes == null || !createOrExistsFile(file)) return false
        var bos: BufferedOutputStream? = null
        try {
            bos = BufferedOutputStream(FileOutputStream(file, append))
            bos.write(bytes)
            return true
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        } finally {
            try {
                bos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    /**
     * 将字节数组写入文件
     *
     * @param filePath The path of file.
     * @param bytes    The bytes.
     * @param isForce  是否写入文件
     * @return `true`: success<br></br>`false`: fail
     */
    fun writeFileFromBytesByChannel(
        filePath: String,
        bytes: ByteArray,
        isForce: Boolean
    ): Boolean {
        return writeFileFromBytesByChannel(getFileByPath(filePath), bytes, false, isForce)
    }

    /**
     * 将字节数组写入文件
     *
     * @param filePath The path of file.
     * @param bytes    The bytes.
     * @param append   True to append, false otherwise.
     * @param isForce  True to force write file, false otherwise.
     * @return `true`: success<br></br>`false`: fail
     */
    fun writeFileFromBytesByChannel(
        filePath: String,
        bytes: ByteArray,
        append: Boolean,
        isForce: Boolean
    ): Boolean {
        return writeFileFromBytesByChannel(getFileByPath(filePath), bytes, append, isForce)
    }

    /**
     * 将字节数组写入文件
     *
     * @param file    The file.
     * @param bytes   The bytes.
     * @param isForce True to force write file, false otherwise.
     * @return `true`: success<br></br>`false`: fail
     */
    fun writeFileFromBytesByChannel(
        file: File,
        bytes: ByteArray,
        isForce: Boolean
    ): Boolean {
        return writeFileFromBytesByChannel(file, bytes, false, isForce)
    }

    /**
     * 将字节数组写入文件
     *
     * @param file    The file.
     * @param bytes   The bytes.
     * @param append  True to append, false otherwise.
     * @param isForce True to force write file, false otherwise.
     * @return `true`: success<br></br>`false`: fail
     */
    fun writeFileFromBytesByChannel(
        file: File?,
        bytes: ByteArray?,
        append: Boolean,
        isForce: Boolean
    ): Boolean {
        if (bytes == null) return false
        var fc: FileChannel? = null
        try {
            fc = FileOutputStream(file, append).channel
            fc!!.position(fc.size())
            fc.write(ByteBuffer.wrap(bytes))
            if (isForce) fc.force(true)
            return true
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        } finally {
            try {
                fc?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    /**
     * 将字节数组写入文件
     *
     * @param filePath The path of file.
     * @param bytes    The bytes.
     * @param isForce  True to force write file, false otherwise.
     * @return `true`: success<br></br>`false`: fail
     */
    fun writeFileFromBytesByMap(
        filePath: String,
        bytes: ByteArray,
        isForce: Boolean
    ): Boolean {
        return writeFileFromBytesByMap(filePath, bytes, false, isForce)
    }

    /**
     * 将字节数组写入文件
     *
     * @param filePath The path of file.
     * @param bytes    The bytes.
     * @param append   True to append, false otherwise.
     * @param isForce  True to force write file, false otherwise.
     * @return `true`: success<br></br>`false`: fail
     */
    fun writeFileFromBytesByMap(
        filePath: String,
        bytes: ByteArray,
        append: Boolean,
        isForce: Boolean
    ): Boolean {
        return writeFileFromBytesByMap(getFileByPath(filePath), bytes, append, isForce)
    }

    /**
     * 将字节数组写入文件
     *
     * @param file    The file.
     * @param bytes   The bytes.
     * @param isForce True to force write file, false otherwise.
     * @return `true`: success<br></br>`false`: fail
     */
    fun writeFileFromBytesByMap(
        file: File,
        bytes: ByteArray,
        isForce: Boolean
    ): Boolean {
        return writeFileFromBytesByMap(file, bytes, false, isForce)
    }

    /**
     * 将字节数组写入文件
     *
     * @param file    The file.
     * @param bytes   The bytes.
     * @param append  True to append, false otherwise.
     * @param isForce True to force write file, false otherwise.
     * @return `true`: success<br></br>`false`: fail
     */
    fun writeFileFromBytesByMap(
        file: File?,
        bytes: ByteArray?,
        append: Boolean,
        isForce: Boolean
    ): Boolean {
        if (bytes == null || !createOrExistsFile(file)) return false
        var fc: FileChannel? = null
        try {
            fc = FileOutputStream(file, append).channel
            val mbb = fc!!.map(FileChannel.MapMode.READ_WRITE, fc.size(), bytes.size.toLong())
            mbb.put(bytes)
            if (isForce) mbb.force()
            return true
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        } finally {
            try {
                fc?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    /**
     * 将字符串写入文件
     *
     * @param filePath The path of file.
     * @param content  The string of content.
     * @return `true`: success<br></br>`false`: fail
     */
    fun writeFileFromString(filePath: String, content: String): Boolean {
        return writeFileFromString(getFileByPath(filePath), content, false)
    }

    /**
     * 将字符串写入文件
     *
     * @param filePath The path of file.
     * @param content  The string of content.
     * @param append   True to append, false otherwise.
     * @return `true`: success<br></br>`false`: fail
     */
    fun writeFileFromString(
        filePath: String,
        content: String,
        append: Boolean
    ): Boolean {
        return writeFileFromString(getFileByPath(filePath), content, append)
    }

    /**
     * 将字符串写入文件
     *
     * @param file    The file.
     * @param content The string of content.
     * @param append  True to append, false otherwise.
     * @return `true`: success<br></br>`false`: fail
     */
    @JvmOverloads
    fun writeFileFromString(
        file: File?,
        content: String?,
        append: Boolean = false
    ): Boolean {
        if (file == null || content == null) return false
        if (!createOrExistsFile(file)) return false
        var bw: BufferedWriter? = null
        try {
            bw = BufferedWriter(FileWriter(file, append))
            bw.write(content)
            return true
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        } finally {
            try {
                bw?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // the divide line of write and read
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 读取文件到字符串链表中
     *
     * @param filePath The path of file.
     * @return the lines in file
     */
    fun readFileToList(filePath: String): List<String>? {
        return readFileToList(getFileByPath(filePath), null)
    }

    /**
     * 读取文件到字符串链表中
     *
     * @param filePath    The path of file.
     * @param charsetName The name of charset.
     * @return the lines in file
     */
    fun readFileToList(filePath: String, charsetName: String): List<String>? {
        return readFileToList(getFileByPath(filePath), charsetName)
    }

    /**
     * 读取文件到字符串链表中
     *
     * @param file        The file.
     * @param charsetName The name of charset.
     * @return the lines in file
     */
    fun readFileToList(file: File?, charsetName: String?): List<String>? {
        return readFileToList(file, 0, 0x7FFFFFFF, charsetName)
    }

    /**
     * 读取文件到字符串链表中
     *
     * @param filePath The path of file.
     * @param st       The line's index of start.
     * @param end      The line's index of end.
     * @return the lines in file
     */
    fun readFileToList(filePath: String, st: Int, end: Int): List<String>? {
        return readFileToList(getFileByPath(filePath), st, end, null)
    }

    /**
     * 读取文件到字符串链表中
     *
     * @param filePath    The path of file.
     * @param st          The line's index of start.
     * @param end         The line's index of end.
     * @param charsetName The name of charset.
     * @return the lines in file
     */
    fun readFileToList(
        filePath: String,
        st: Int,
        end: Int,
        charsetName: String
    ): List<String>? {
        return readFileToList(getFileByPath(filePath), st, end, charsetName)
    }

    /**
     * 读取文件到字符串链表中
     *
     * @param file        The file.
     * @param st          The line's index of start.
     * @param end         The line's index of end.
     * @param charsetName The name of charset.
     * @return the lines in file
     */
    @JvmOverloads
    fun readFileToList(
        file: File?,
        st: Int = 0,
        end: Int = 0x7FFFFFFF,
        charsetName: String? = null
    ): List<String>? {
        if (!isFileExists(file)) return null
        if (st > end) return null
        var reader: BufferedReader? = null
        try {
            var line = ""
            var curLine = 1
            val list = ArrayList<String>()
            reader = if (isSpace(charsetName)) {
                BufferedReader(InputStreamReader(FileInputStream(file)))
            } else {
                BufferedReader(
                    InputStreamReader(FileInputStream(file), charsetName)
                )
            }
            while (({ line = reader.readLine() }) != null) {
                if (curLine > end) break
                if (curLine in st..end) list.add(line)
                ++curLine
            }
            return list
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            try {
                reader?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    /**
     * 读取文件到字符串中
     *
     * @param filePath The path of file.
     * @return the string in file
     */
    fun readFileToString(filePath: String): String? {
        return readFileToString(getFileByPath(filePath), null)
    }

    /**
     * 读取文件到字符串中
     *
     * @param filePath    The path of file.
     * @param charsetName The name of charset.
     * @return the string in file
     */
    fun readFileToString(filePath: String, charsetName: String): String? {
        return readFileToString(getFileByPath(filePath), charsetName)
    }

    /**
     * 读取文件到字符串中
     *
     * @param file        The file.
     * @param charsetName The name of charset.
     * @return the string in file
     */
    @JvmOverloads
    fun readFileToString(file: File?, charsetName: String? = null): String? {
        if (!isFileExists(file)) return null
        var reader: BufferedReader? = null
        try {
            val sb = StringBuilder()
            if (isSpace(charsetName)) {
                reader = BufferedReader(InputStreamReader(FileInputStream(file)))
            } else {
                reader = BufferedReader(
                    InputStreamReader(FileInputStream(file), charsetName)
                )
            }
            var line = ""
            if (({ line = reader.readLine() }) != null) {
                sb.append(line)
                while (({ line = reader.readLine() }) != null) {
                    sb.append(LINE_SEP).append(line)
                }
            }
            return sb.toString()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            try {
                reader?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    /**
     * 读取文件到字节数组中
     *
     * @param filePath The path of file.
     * @return the bytes in file
     */
    fun readFileToBytesByStream(filePath: String): ByteArray? {
        return readFileToBytesByStream(getFileByPath(filePath))
    }

    /**
     * 读取文件到字节数组中
     *
     * @param file The file.
     * @return the bytes in file
     */
    fun readFileToBytesByStream(file: File?): ByteArray? {
        if (!isFileExists(file)) return null
        var fis: FileInputStream? = null
        var os: ByteArrayOutputStream? = null
        try {
            fis = FileInputStream(file)
            os = ByteArrayOutputStream()
            val b = ByteArray(sBufferSize)
            var len = 0
            while (({ len = fis.read(b, 0, sBufferSize);len }()) != -1) {
                os.write(b, 0, len)
            }
            return os.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            try {
                fis?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            try {
                os?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    /**
     * 读取文件到字节数组中
     *
     * @param filePath The path of file.
     * @return the bytes in file
     */
    fun readFileToBytesByChannel(filePath: String): ByteArray? {
        return readFileToBytesByChannel(getFileByPath(filePath))
    }

    /**
     * 读取文件到字节数组中
     *
     * @param file The file.
     * @return the bytes in file
     */
    fun readFileToBytesByChannel(file: File?): ByteArray? {
        if (!isFileExists(file)) return null
        var fc: FileChannel? = null
        try {
            fc = RandomAccessFile(file, "r").channel
            val byteBuffer = ByteBuffer.allocate(fc!!.size().toInt())
            while (true) {
                if (fc.read(byteBuffer) <= 0) break
            }
            return byteBuffer.array()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            try {
                fc?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    /**
     * 读取文件到字节数组中
     *
     * @param filePath The path of file.
     * @return the bytes in file
     */
    fun readFileToBytesByMap(filePath: String): ByteArray? {
        return readFileToBytesByMap(getFileByPath(filePath))
    }

    /**
     * 读取文件到字节数组中
     *
     * @param file The file.
     * @return the bytes in file
     */
    fun readFileToBytesByMap(file: File?): ByteArray? {
        if (!isFileExists(file)) return null
        var fc: FileChannel? = null
        try {
            fc = RandomAccessFile(file, "r").channel
            val size = fc!!.size().toInt()
            val mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, size.toLong()).load()
            val result = ByteArray(size)
            mbb.get(result, 0, size)
            return result
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            try {
                fc?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    /**
     * 设置缓冲区尺寸
     *
     * Default size equals 8192 bytes.
     *
     * @param bufferSize The buffer's size.
     */
    fun setBufferSize(bufferSize: Int) {
        sBufferSize = bufferSize
    }

    private fun getFileByPath(filePath: String): File? {
        return if (isSpace(filePath)) null else File(filePath)
    }

    private fun createOrExistsFile(filePath: String): Boolean {
        return createOrExistsFile(getFileByPath(filePath))
    }

    private fun createOrExistsFile(file: File?): Boolean {
        if (file == null) return false
        if (file.exists()) return file.isFile
        if (!createOrExistsDir(file.parentFile)) return false
        try {
            return file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }

    }

    private fun createOrExistsDir(file: File?): Boolean {
        return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
    }

    private fun isFileExists(file: File?): Boolean {
        return file != null && file.exists()
    }

    private fun isSpace(s: String?): Boolean {
        if (s == null) return true
        var i = 0
        val len = s.length
        while (i < len) {
            if (!Character.isWhitespace(s[i])) {
                return false
            }
            ++i
        }
        return true
    }
}
/**
 * 将输入流写入文件
 *
 * @param file The file.
 * @param is   The input stream.
 * @return `true`: success<br></br>`false`: fail
 */
/**
 * 将字节数组写入文件
 *
 * @param file  The file.
 * @param bytes The bytes.
 * @return `true`: success<br></br>`false`: fail
 */
/**
 * 将字符串写入文件
 *
 * @param file    The file.
 * @param content The string of content.
 * @return `true`: success<br></br>`false`: fail
 */
/**
 * 读取文件到字符串链表中
 *
 * @param file The file.
 * @return the lines in file
 */
/**
 * 读取文件到字符串链表中
 *
 * @param file The file.
 * @param st   The line's index of start.
 * @param end  The line's index of end.
 * @return the lines in file
 */
/**
 * 读取文件到字符串中
 *
 * @param file The file.
 * @return the string in file
 */
