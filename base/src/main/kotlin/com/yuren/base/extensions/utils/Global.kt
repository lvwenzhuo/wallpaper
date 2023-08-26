package com.yuren.base.extensions.utils

import android.os.Handler
import android.os.Looper

class SafeHandler : Handler(Looper.myLooper() ?: Looper.getMainLooper())

fun postDelayed(delay: Long, action: () -> Unit) {
    SafeHandler().postDelayed(action, delay)
}

private fun isOnMainThread() = Looper.myLooper() == Looper.getMainLooper()

internal fun ensureBackgroundThread(callback: () -> Unit) {
    if (isOnMainThread()) {
        Thread { callback() }.start()
    } else {
        callback()
    }
}

