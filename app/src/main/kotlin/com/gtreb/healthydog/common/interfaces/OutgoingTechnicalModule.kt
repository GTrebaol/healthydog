package com.gtreb.healthydog.common.interfaces

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.google.android.play.core.ktx.launchReview
import com.google.android.play.core.ktx.requestReview
import com.google.android.play.core.review.ReviewManagerFactory
import com.gtreb.healthydog.common.implementation.TimberMonitor
import com.gtreb.healthydog.common.navigation.IDispatcherService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class OutgoingTechnicalModule(
    private val monitor: TimberMonitor,
    private val dispatchers: IDispatcherService,
    private val context: Context
) : IOutgoingModule {

    companion object {
        /**
         * Intent constants
         */
        private const val ACTION_DIAL_TEL = "tel:"

        /**
         * Error messages
         */
        private const val INIT_ACTIVITY_ERROR =
            "IOutgoingTechnicalModule.Params.ACTVITY must be an Actvity"
        private const val INIT_PHONE_NUMBER_ERROR =
            "IOutgoingTechnicalModule.Params.PHONE_NUMBER must be a String"
    }

    override var technicalCallback: IOutgoingTechnicalCallback =
        object : IOutgoingTechnicalCallback {
            override fun onStartIntentCallPhoneNumberFailure(arguments: Map<String, Any?>) {
                monitor.logE("onStartIntentCallPhoneNumberFailure should be overridden by the technicalCallback setter !")
            }

            override fun onStartIntentCallPhoneNumberSuccess(arguments: Map<String, Any?>) {
                monitor.logE("onStartIntentCallPhoneNumberSuccess should be overridden by the technicalCallback setter !")
            }

        }

    override fun startIntentCallPhoneNumber(arguments: Map<String, Any?>) {
        if (arguments[IOutgoingModule.Params.PHONE_NUMBER] !is String) {
            monitor.logE(INIT_PHONE_NUMBER_ERROR)
            technicalCallback.onStartIntentCallPhoneNumberFailure()
            return
        }

        val phoneNumber: String = arguments[IOutgoingModule.Params.PHONE_NUMBER] as String

        Intent(Intent.ACTION_DIAL, Uri.parse(ACTION_DIAL_TEL + phoneNumber)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            try {
                startActivity(context, this, null)
                technicalCallback.onStartIntentCallPhoneNumberSuccess()
            } catch (e: ActivityNotFoundException) {
                e.message?.let { monitor.logE(it) }
                technicalCallback.onStartIntentCallPhoneNumberFailure()
            }
        }
    }


    override fun startRatingApplication(arguments: Map<String, Any?>) {
        val activity: Activity = (arguments[IOutgoingModule.Params.ACTIVITY] as? Activity) ?: run {
            monitor.logE(INIT_ACTIVITY_ERROR)
            return
        }
        CoroutineScope(dispatchers.default).launch {
            try {
                val reviewManager = ReviewManagerFactory.create(activity)
                reviewManager.launchReview(activity, reviewManager.requestReview())
            } catch (e: Exception) {
                monitor.logE("Error occurred while launching rating application flow. Message: ${e.message}")
            }
        }
    }
}
