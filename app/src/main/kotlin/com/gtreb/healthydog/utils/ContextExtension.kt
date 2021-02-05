package com.gtreb.healthydog.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * Context extension to show toast with only resId parameter
 * @param resId The string resource to show
 * @param duration Default to Toast.LENGTH_SHORT, could be Toast.LENGTH_LONG
 */
fun Context.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT): Toast = Toast.makeText(this, resId, duration).also { it.show() }

/**
 * Context extension to show toast with only text parameter
 * @param text The string text to show
 * @param duration Default to Toast.LENGTH_SHORT, could be Toast.LENGTH_LONG
 */
fun Context.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT): Toast = Toast.makeText(this, text, duration).also { it.show() }

/**
 * Context extension to show toast with only resId parameter but for a duration fixed to Toast.LENGTH_LONG
 * @param resId The string resource to show
 */
fun Context.longToast(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
}

/**
 * Context extension to show toast with only text parameter but for a duration fixed to Toast.LENGTH_LONG
 * @param text The string text to show
 */
fun Context.longToast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}
