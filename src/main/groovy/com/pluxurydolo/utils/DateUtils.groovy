package com.pluxurydolo.utils

class DateUtils {
    static String getCurrentDate() {
        Date now = new Date()
        return now.format('yyyy-MM-dd')
    }
}
