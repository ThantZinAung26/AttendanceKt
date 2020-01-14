package com.soft.attendancekt.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutor {

    companion object {
        private var ioExecutor: Executor? = null
        var mainExecutor: Executor? = null

        fun io(): Executor? {
            if (null == ioExecutor) {
                ioExecutor = Executors.newFixedThreadPool(3)
            }
            return ioExecutor
        }
    }

    fun main(): Executor? {
        if (null == mainExecutor) {
            mainExecutor = MainExecutor()
        }
        return mainExecutor
    }

    inner class MainExecutor : Executor {

        private val mHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mHandler.post(command)
        }

    }

}