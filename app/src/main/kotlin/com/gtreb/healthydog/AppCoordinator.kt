package com.gtreb.healthydog

import android.content.Context
import android.widget.Toast
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

    private val dashboard: DashBoardModule.ModuleEntry by inject()
    private val accountsOverview: AccountsOverviewModule.ModuleEntry by inject()
    private val smi: SmiModule.ModuleEntry by inject()
    private val outgoing: IOutgoingTechnicalModule by inject()
    private val menu: MenuModule.ModuleEntry by inject()

    // region Home

    internal fun goToDashboard() = dashboard.startDashBoard()
    internal fun goToMenu() = menu.startMenu()


    internal fun goToWeb(url: String?) {
        url ?: return showError(::goToWeb.name, NO_URL)
        outgoing.startIntentOpenUrl(
            mapOf(
                IOutgoingTechnicalModule.Params.CONTEXT to context,
                IOutgoingTechnicalModule.Params.URL to url
            )
        )
    }

    internal fun goToPhone(phoneNumber: String?) {
        phoneNumber ?: return showError(::goToPhone.name, NO_PHONE_NUMBER)
        outgoing.startIntentCallPhoneNumber(
            mapOf(
                IOutgoingTechnicalModule.Params.CONTEXT to context,
                IOutgoingTechnicalModule.Params.PHONE_NUMBER to phoneNumber
            )
        )
    }

    internal fun launchApplication(packageName: String?) {
        packageName ?: return showError(::launchApplication.name, NO_PACKAGE_NAME)
        outgoing.launchApplication(
            mapOf(
                IOutgoingTechnicalModule.Params.CONTEXT to context,
                IOutgoingTechnicalModule.Params.PACKAGE_NAME to packageName
            )
        )
    }



    internal fun goToSMI() {
        smi.startSmi()
    }

    internal fun goToRatingApplication() {
        outgoing.startRatingApplication(mapOf(IOutgoingTechnicalModule.Params.ACTIVITY to activityHolder.activity))
    }

    // region ErrorHandling

    private fun showError(functionName: String, message: String) {
        ToastUtils.showToast(
            context,
            context.resources.getString(R.string.error_occurred),
            Toast.LENGTH_SHORT
        )
        monitor.logE("Error occurred in $functionName : $message")
    }

    // endregion ErrorHandling

    internal fun unimplemented() {
        ToastUtils.showToast(
            context,
            context.resources.getString(R.string.soon_available),
            Toast.LENGTH_SHORT
        )
    }
}