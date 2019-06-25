package com.example.lib_wedgit.utils

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-04 : 16:41<br/>
 */
object ShellUtils {
    val LINE_SEP = System.getProperty("line.separator")
    /**
     * 是否是在 root 下执行命令
     *
     * @param command The command.
     * @param isRoot  True to use root, false otherwise.
     * @return the single [CommandResult] instance
     */
    fun execCmd(command: String, isRoot: Boolean): CommandResult {
        return execCmd(arrayOf(command), isRoot, true)
    }

    /**
     * 是否是在 root 下执行命令
     *
     * @param commands The commands.
     * @param isRoot   True to use root, false otherwise.
     * @return the single [CommandResult] instance
     */
    fun execCmd(commands: List<String>?, isRoot: Boolean): CommandResult {
        return execCmd(commands?.toTypedArray(), isRoot, true)
    }

    /**
     * 是否是在 root 下执行命令
     *
     * @param commands The commands.
     * @param isRoot   True to use root, false otherwise.
     * @return the single [CommandResult] instance
     */
    fun execCmd(commands: Array<String>, isRoot: Boolean): CommandResult {
        return execCmd(commands, isRoot, true)
    }

    /**
     * 是否是在 root 下执行命令
     *
     * @param command         The command.
     * @param isRoot          True to use root, false otherwise.
     * @param isNeedResultMsg True to return the message of result, false otherwise.
     * @return the single [CommandResult] instance
     */
    fun execCmd(
        command: String,
        isRoot: Boolean,
        isNeedResultMsg: Boolean
    ): CommandResult {
        return execCmd(arrayOf(command), isRoot, isNeedResultMsg)
    }

    /**
     * 是否是在 root 下执行命令
     *
     * @param commands        The commands.
     * @param isRoot          True to use root, false otherwise.
     * @param isNeedResultMsg True to return the message of result, false otherwise.
     * @return the single [CommandResult] instance
     */
    fun execCmd(
        commands: List<String>?,
        isRoot: Boolean,
        isNeedResultMsg: Boolean
    ): CommandResult {
        return execCmd(
            commands?.toTypedArray(),
            isRoot,
            isNeedResultMsg
        )
    }

    /**
     * 是否是在 root 下执行命令
     *
     * @param commands        The commands.
     * @param isRoot          True to use root, false otherwise.
     * @param isNeedResultMsg True to return the message of result, false otherwise.
     * @return the single [CommandResult] instance
     */
    fun execCmd(commands: Array<String>?, isRoot: Boolean, isNeedResultMsg: Boolean): CommandResult {
        var result = -1
        if (commands == null || commands.isEmpty()) {
            return CommandResult(result, null, null)
        }
        var process: Process? = null
        var successResult: BufferedReader? = null
        var errorResult: BufferedReader? = null
        var successMsg: StringBuilder? = null
        var errorMsg: StringBuilder? = null
        var os: DataOutputStream? = null
        try {
            process = Runtime.getRuntime().exec(if (isRoot) "su" else "sh")
            os = DataOutputStream(process!!.outputStream)
            for (command in commands) {
                os.write(command.toByteArray())
                os.writeBytes(LINE_SEP)
                os.flush()
            }
            os.writeBytes("exit$LINE_SEP")
            os.flush()
            result = process.waitFor()
            if (isNeedResultMsg) {
                successMsg = StringBuilder()
                errorMsg = StringBuilder()
                successResult = BufferedReader(
                    InputStreamReader(
                        process.inputStream,
                        "UTF-8"
                    )
                )
                errorResult = BufferedReader(
                    InputStreamReader(
                        process.errorStream,
                        "UTF-8"
                    )
                )

            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            CloseUtils.closeIO(arrayOf(os!!, successResult!!, errorResult!!))
            process?.destroy()
        }
        return CommandResult(
            result,
            successMsg?.toString(),
            errorMsg?.toString()
        )
    }

    /**
     * The result of command.
     */
    class CommandResult(var result: Int, var successMsg: String?, var errorMsg: String?)
}