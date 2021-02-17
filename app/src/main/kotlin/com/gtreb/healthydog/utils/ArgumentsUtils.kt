package com.gtreb.healthydog.common

import android.os.Binder
import android.os.Bundle
import android.os.Parcelable
import android.util.Size
import android.util.SizeF
import java.io.Serializable

/**
 * Transform a map arguments into a bundle.
 *
 * There is some limitations. It do not transform :
 *  - Array<*>, Array of Parcelable or ArrayList
 *  - Any custom Types that bundle do not handle.
 */
fun <V> Map<String, V>.toBundle(): Bundle = Bundle().apply {
    forEach { (key, value) ->
        when (value) {
            is Boolean -> putBoolean(key, value)
            is Byte -> putByte(key, value)
            is Char -> putChar(key, value)
            is Short -> putShort(key, value)
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is Double -> putDouble(key, value)
            is String -> putString(key, value)
            is CharSequence -> putCharSequence(key, value)
            is Serializable -> putSerializable(key, value)
            is BooleanArray -> putBooleanArray(key, value)
            is ByteArray -> putByteArray(key, value)
            is ShortArray -> putShortArray(key, value)
            is CharArray -> putCharArray(key, value)
            is IntArray -> putIntArray(key, value)
            is LongArray -> putLongArray(key, value)
            is FloatArray -> putFloatArray(key, value)
            is DoubleArray -> putDoubleArray(key, value)
            is Parcelable -> putParcelable(key, value)
            is Binder -> putBinder(key, value)
            is Bundle -> putBundle(key, value)
            is Size -> putSize(key, value)
            is SizeF -> putSizeF(key, value)
        }
    }
}
