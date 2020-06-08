package com.wutsi.core.util

object DurationUtils {
    fun secondsToHumanReadable(timeInSeconds: Long): String {
        if (timeInSeconds == 0L) {
            return ""
        } else if (timeInSeconds < 60) {
            return "${timeInSeconds}s"
        } else if (timeInSeconds < 3600) {
            val minute = timeInSeconds / 60
            val seconds = timeInSeconds % 60
            return if (seconds == 0L) "${minute}m" else "${minute}m ${seconds}s"
        } else {
            val hours = timeInSeconds / 3600
            val minute = (timeInSeconds % 3600)/60
            return if (minute == 0L) "${hours}h" else "${hours}h ${minute}m"
        }

    }
}
