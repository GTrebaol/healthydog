package com.gtreb.healthydog

import android.content.Context
import android.widget.Toast
import com.gtreb.healthydog.AppCoordinator.Companion.NO_PACKAGE_NAME
import com.gtreb.healthydog.common.IMonitorTechnicalModule
import com.gtreb.healthydog.common.IOutgoingModule
import com.gtreb.healthydog.dashboard.DashBoardModule
import com.gtreb.healthydog.utils.longToast
import com.gtreb.healthydog.utils.toast
import org.koin.core.KoinComponent
import org.koin.core.inject

class AppCoordinator(private val context: Context, val activityHolder: ActivityHolder) :
    KoinComponent {
    companion object {
        const val NO_ACCOUNT_ID = "Tried to open without account ID"
        const val NO_CARD_ID = "Tried to open without card ID"
        const val NO_URL = "Tried to open without URL"
        const val NO_PHONE_NUMBER = "Tried to open without phone number"
        const val NO_PACKAGE_NAME = "Tried to open without package name"
    }

    // endregion InternalNavigation


    private val monitor: IMonitorTechnicalModule.LoggingAction by inject()
    private val dashboard: DashBoardModule.ModuleEntry by inject()
    private val outgoing: IOutgoingModule by inject()
    /*
    private val smi: SmiModule.ModuleEntry by inject()
    private val outgoing: IOutgoingModule by inject()
    private val menu: MenuModule.ModuleEntry by inject()*/

    // region Home

    internal fun goToDashboard() = dashboard.startDashBoard()


    internal fun launchApplication(packageName: String?) {
        packageName ?: return showError(::launchApplication.name, NO_PACKAGE_NAME)
        outgoing.launchApplication(
            mapOf(
                IOutgoingModule.Params.CONTEXT to context,
                IOutgoingModule.Params.PACKAGE_NAME to packageName
            )
        )
    }


    internal fun goToRatingApplication() {
        outgoing.startRatingApplication(mapOf(IOutgoingModule.Params.ACTIVITY to activityHolder.activity))
    }

    // region ErrorHandling

    private fun showError(functionName: String, message: String) {
        context.longToast(
            context.resources.getString(R.string.error_occurred),
        )
        monitor.logE("Error occurred in $functionName : $message")
    }

    // endregion ErrorHandling

    internal fun unimplemented() {
        context.toast(
            context.resources.getString(R.string.soon_available),
            Toast.LENGTH_SHORT
        )
    }
}