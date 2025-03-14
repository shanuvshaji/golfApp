package com.example.sampleapp.utils

import android.os.SystemClock

object ClickUtils {
    var BUTTON_CLICK = "sclick:button-click"
    var REFRESH_CLICK = "sclick:refresh-click"
    var NAVIGATION_CLICK = "sclick:navigation-click"
    var APP_EXIT = "click:app-exit"
    var TIME_500 = 500
    var TIME_1000 = 1000
    var TIME_2000 = 2000
    var mHashmap: HashMap<String, Long>? = HashMap()
    @JvmOverloads
    fun isSingleClick(): Boolean {
        var result = false
        val current = SystemClock.elapsedRealtime()
        if (mHashmap != null && mHashmap!!.containsKey(BUTTON_CLICK)) {
            val last = mHashmap!![BUTTON_CLICK]!!
            if (last != -1L && current - last >= TIME_500) {
                result = true
            }
        } else {
            result = true
        }
        if (result) {
            if (mHashmap == null) {
                mHashmap = HashMap()
            }
            mHashmap!![BUTTON_CLICK] = current
        }
        return result
    }

    init {
        mHashmap = HashMap()
    }
}