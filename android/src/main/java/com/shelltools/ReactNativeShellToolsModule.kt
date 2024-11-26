package com.shelltools

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactMethod
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.IOException
import java.io.InputStream
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap

@ReactModule(name = ReactNativeShellToolsModule.NAME)
class ReactNativeShellToolsModule(reactContext: ReactApplicationContext) :
  NativeReactNativeShellToolsSpec(reactContext)  {

  override fun getName(): String {
    return NAME
  }

  override fun execCommand(command: String): String {
    val output = runCommand(command)
    return output
  }

  override fun execAsyncCommand(command: String, promise: Promise) {
    val thread = Thread {
        try {
            val output = runCommand(command)
            promise.resolve(output)
        } catch (e: Exception) {
            val errorMap: WritableMap = Arguments.createMap()
            errorMap.putString("command", command)
            errorMap.putString("error_message", e.message)

            promise.reject("ERR_EXEC_COMMAND", e, errorMap)
        }
    }

    try {
        thread.start()
    } catch (e: Exception) {
        val errorMap: WritableMap = Arguments.createMap()
        errorMap.putString("command", command)
        errorMap.putString("error_message", e.message)

        promise.reject("ERR_THREAD_START", e, errorMap)
    }
}

private fun runCommand(command: String): String {
    var process: Process? = null
    var output = StringBuilder()
    var error = StringBuilder()

    try {
        // 检查是否已获得 root 权限，如果有则添加 su 前缀
        val finalCommand = if (isRooted()) {
            // 以 su 权限执行命令
            arrayOf("su", "-c", command)
        } else {
            arrayOf(command)
        }

        // 执行命令
        process = Runtime.getRuntime().exec(finalCommand)

        // 使用线程处理标准输出和错误输出，防止死锁
        val outputThread = Thread {
            output.append(getProcessOutput(process.inputStream)) // 传递正确的流
        }
        val errorThread = Thread {
            error.append(getProcessOutput(process.errorStream)) // 传递正确的流
        }

        outputThread.start()
        errorThread.start()

        // 等待进程结束
        process.waitFor()

        // 等待输出和错误处理完成
        outputThread.join()
        errorThread.join()

    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: InterruptedException) {
        e.printStackTrace()
    } finally {
        process?.destroy()
    }

    // 返回合并后的输出和错误
    return output.toString() + error.toString()
}

// 工具方法：读取进程输出
private fun getProcessOutput(inputStream: InputStream): String {
        val reader = BufferedReader(InputStreamReader(inputStream))
        val output = StringBuilder()
        var line: String?
    
        try {
            while (reader.readLine().also { line = it } != null) {
                output.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    
        return output.toString()
}

// 检查设备是否已获得 root 权限
private fun isRooted(): Boolean {
    return try {
        // 尝试执行一个简单的 su 命令，检查是否有 root 权限
        val process = Runtime.getRuntime().exec(arrayOf("which", "su"))
        process.waitFor() == 0
    } catch (e: Exception) {
        false
    }
}

  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  override fun multiply(a: Double, b: Double): Double {
    return a * b
  }

  companion object {
    const val NAME = "ReactNativeShellTools"
  }
}
