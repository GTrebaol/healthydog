package com.gtreb.healthydog.common.implementations

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import com.gtreb.healthydog.R
import com.gtreb.healthydog.utils.Constants.REQUEST_CODE_ACCESS_COARSE_LOCATION
import com.gtreb.healthydog.utils.Constants.REQUEST_CODE_ACCESS_FINE_LOCATION


sealed class PermissionHandler(
    val permissionName: String,
    val requestCode: Int,
    val deniedMessageId: Int,
    val explanationMessageId: Int
) {
    companion object {
        val PERMISSION_HANDLERS: List<PermissionHandler> by lazy {
            listOf(
                AccessCoarseLocation,
                AccessFineLocation
            )
        }
    }


    object AccessFineLocation : PermissionHandler(
        Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_CODE_ACCESS_FINE_LOCATION,
        R.string.permission_required_text, R.string.permission_required_text
    )

    object AccessCoarseLocation : PermissionHandler(
        Manifest.permission.ACCESS_COARSE_LOCATION, REQUEST_CODE_ACCESS_COARSE_LOCATION,
        R.string.permission_required_text, R.string.permission_required_text
    )

}


fun Fragment.isGranted(permissionHandler: PermissionHandler) = run {
    context?.let {
        (PermissionChecker.checkSelfPermission(
            it, permissionHandler.permissionName
        ) == PermissionChecker.PERMISSION_GRANTED)
    } ?: false
}

fun Fragment.shouldShowRationale(permissionHandler: PermissionHandler) = run {
    shouldShowRequestPermissionRationale(permissionHandler.permissionName)
}

fun Fragment.requestPermission(permissionHandler: PermissionHandler): ActivityResultLauncher<String> {
    return registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            this.isGranted(permissionHandler)
        } else {
            this.shouldShowRationale(permissionHandler)
        }
    }
}


fun Fragment.handlePermission(
    permissionHandler: PermissionHandler,
    onGranted: (PermissionHandler) -> Unit,
    onDenied: (PermissionHandler) -> Unit,
    onRationaleNeeded: ((PermissionHandler) -> Unit)? = null
) {
    when {
        isGranted(permissionHandler) -> onGranted.invoke(permissionHandler)
        shouldShowRationale(permissionHandler) -> onRationaleNeeded?.invoke(permissionHandler)
        else -> onDenied.invoke(permissionHandler)
    }
}

fun Fragment.handlePermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray,
    onPermissionGranted: (PermissionHandler) -> Unit,
    onPermissionDenied: ((PermissionHandler) -> Unit)? = null,
    onPermissionDeniedPermanently: ((PermissionHandler) -> Unit)? = null
) {

    PermissionHandler.PERMISSION_HANDLERS.find {
        it.requestCode == requestCode
    }?.let { appPermission ->
        val permissionGrantResult = mapPermissionsAndResults(
            permissions, grantResults
        )[appPermission.permissionName]
        when {
            PermissionChecker.PERMISSION_GRANTED == permissionGrantResult -> {
                onPermissionGranted(appPermission)
            }
            shouldShowRationale(appPermission) -> onPermissionDenied?.invoke(appPermission)
            else -> {
                goToAppDetailsSettings()
                onPermissionDeniedPermanently?.invoke(appPermission)
            }
        }
    }
}

private fun Fragment.goToAppDetailsSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context?.packageName, null)
    }
    activity?.let {
        it.startActivityForResult(intent, 0)
    }
}


private fun mapPermissionsAndResults(
    permissions: Array<out String>, grantResults: IntArray
): Map<String, Int> = permissions.mapIndexedTo(
    mutableListOf<Pair<String, Int>>()
) { index, permission -> permission to grantResults[index] }.toMap()
