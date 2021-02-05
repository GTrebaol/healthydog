package com.gtreb.healthydog.common


interface IOutgoingModule {

    /** All parameters (map keys) used by Outgoing Module */
    object Params {
        const val CONTEXT = "context"
        const val ACTIVITY = "activity"
        const val URL = "url"
        const val PHONE_NUMBER = "phoneNumber"
        const val WEB_VIEW = "webView"
        const val WEB_VIEW_CLIENT = "webViewClient"
        const val PACKAGE_NAME = "packageName"
    }

    /**
     * The module must have a callback class to listen exit points
     */
    var technicalCallback: IOutgoingTechnicalCallback

    /**
     * Start an intent for opening an url in browser
     * @param arguments Depend on the core app needs, could be anything or nothing
     */
    fun startIntentOpenUrl(arguments: Map<String, Any?> = mapOf())

    /**
     * Start an intent for calling phone number with native phone app
     * @param arguments Depend on the core app needs, could be anything or nothing
     */
    fun startIntentCallPhoneNumber(arguments: Map<String, Any?> = mapOf())

    /**
     * Start a basic webView
     * @param arguments Depend on the core app needs, could be anything or nothing
     */
    fun startBasicWebView(arguments: Map<String, Any?> = mapOf())

    /**
     * Launch application or open store if not installed
     * May use [Params.CONTEXT], [Params.PACKAGE_NAME]
     **/
    fun launchApplication(arguments: Map<String, Any?> = mapOf())

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
     * Called when an intent open url is started
     * @param arguments Depend on the core app needs, could be anything or nothing
     */
    fun onStartIntentOpenUrlSuccess(arguments: Map<String, Any?> = mapOf())

    /**
     * Called when an intent open url failed to start
     * @param arguments Depend on the core app needs, could be anything or nothing
     */
    fun onStartIntentOpenUrlFailure(arguments: Map<String, Any?> = mapOf())

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

    /**
     * Called when a web view is started
     * @param arguments Depend on the core app needs, could be anything or nothing
     */
    fun onStartWebViewSuccess(arguments: Map<String, Any?> = mapOf())

    /**
     * Called when a web view failed to start
     * @param arguments Depend on the core app needs, could be anything or nothing
     */
    fun onStartWebViewFailure(arguments: Map<String, Any?> = mapOf())

    /**
     * Called when an intent open application is started
     * @param arguments Depend on the core app needs, could be anything or nothing
     */
    fun onLaunchApplicationSuccess(arguments: Map<String, Any?> = mapOf())

    /**
     * Called when an intent open application failed to start
     * @param arguments Depend on the core app needs, could be anything or nothing
     */
    fun onLaunchApplicationFailure(arguments: Map<String, Any?> = mapOf())

}