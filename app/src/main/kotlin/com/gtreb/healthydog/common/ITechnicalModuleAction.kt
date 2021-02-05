package com.gtreb.healthydog.common

/**
 * Interface that represents all the possible action of a technical module
 */
abstract class ITechnicalModuleAction<T : ITechnicalModuleCallback> {

    /**
     * Refer to multiple callbacks to be call when an action is over
     */
    private val callbacks = ArrayList<T>()

    /**
     * Add a callback to the callbacks list
     * @param callback The callback to add, must be the same type T
     */
    fun registerCallback(callback: T) {
        callbacks.add(callback)
    }

    /**
     * Remove a callback to the callbacks list
     * @param callback The callback to add, must be the same type T
     */
    fun unregisterCallback(callback: T) {
        callbacks.remove(callback)
    }

    /**
     * Trigger all the registered callbacks
     * @param action Must be a method of class T that is a callback
     * @param autoUnregister True to unregister the callback after the trigger, false otherwise
     */
    fun triggerCallbacks(action: (T) -> Unit, autoUnregister: Boolean = false) {
        callbacks.forEach {
            action.invoke(it)
            if (autoUnregister) unregisterCallback(it)
        }
    }


}
