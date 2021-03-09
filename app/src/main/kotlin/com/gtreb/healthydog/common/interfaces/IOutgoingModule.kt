package com.gtreb.healthydog.common.interfaces


interface IOutgoingModule {

    /** All parameters (map keys) used by Outgoing Module */
    object Params {
        const val CONTEXT = "context"
        const val ACTIVITY = "activity"
        const val PHONE_NUMBER = "phoneNumber"
    }

    /**
     * The module must have a callback class to listen exit points
     */
    var technicalCallback: IOutgoingTechnicalCallback


    /**
     * Start an intent for calling phone number with native phone app
     * @param arguments Depend on the core app needs, could be anything or nothing
     */
    fun startIntentCallPhoneNumber(arguments: Map<String, Any?> = mapOf())


    /**
     * Should launch a request to rate the app on playstore.
     * May use [Params.CONTEXT], [Params.ACTIVITY]
     */
    fun startRatingApplication(arguments: Map<String, Any?> = mapOf())
}

/**
 * Define all the exit points for outgoing technical module
 */
interface IOutgoingTechnicalCallback {

    /**
     * Called when an intent call phone number is started
     * @param arguments Depend on the core app needs, could be anything or nothing
     */
    fun onStartIntentCallPhoneNumberSuccess(arguments: Map<String, Any?> = mapOf())

    /**
     * Called when an intent call phone number failed to start
     * @param arguments Depend on the core app needs, could be anything or nothing
     */
    fun onStartIntentCallPhoneNumberFailure(arguments: Map<String, Any?> = mapOf())


}