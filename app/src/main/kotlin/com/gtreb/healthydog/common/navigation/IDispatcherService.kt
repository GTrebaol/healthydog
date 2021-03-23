package com.gtreb.healthydog.common.navigation

import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue

interface IDispatcherService {

    /** Qualifier for Default implementation */
    object Default : Qualifier {
        override val value: QualifierValue get() = "DispatcherService.Default"
    }

    /** Qualifier for Test implementation */
    object Test : Qualifier {
        override val value: QualifierValue get() = "DispatcherService.Test"
    }

    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}
