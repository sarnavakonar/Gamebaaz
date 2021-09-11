package com.example.gamebaaz.util

import android.util.Base64
import java.io.UnsupportedEncodingException

object Utils {

    fun getBase64(text: String): String {
        var data = ByteArray(0)
        try {
            data = text.toByteArray(charset("UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return Base64.encodeToString(data, Base64.NO_WRAP)
    }
}