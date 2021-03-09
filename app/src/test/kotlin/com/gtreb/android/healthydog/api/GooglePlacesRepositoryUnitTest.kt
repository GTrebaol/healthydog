package com.gtreb.android.healthydog.api

import com.gtreb.android.healthydog.DummyCall
import com.gtreb.healthydog.api.GoogleMapsApi
import com.gtreb.healthydog.common.implementation.TimberMonitor
import com.gtreb.healthydog.model.VeterinarySearchResponse
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach

@ExperimentalCoroutinesApi
class GooglePlacesRepositoryUnitTest {

    @MockK
    private lateinit var googleMapsApi: GoogleMapsApi

    @RelaxedMockK
    private lateinit var logger: TimberMonitor

    companion object {
        const val LOCATION = "41,52"
        const val RADIUS = 500
        const val TYPE = "TYPE"
        const val KEY = "KEY"
        const val SENSOR = false
    }

    @BeforeEach
    fun before() {
        every {
            googleMapsApi.findNearbyPlaces(
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns DummyCall.success {
            VeterinarySearchResponse(emptyArray(), "OK")
        }
    }

    @Test
    fun test() = runBlockingTest {

        verify(exactly = 1) {
            googleMapsApi.findNearbyPlaces(
                location = LOCATION,
                radius = RADIUS,
                type = TYPE,
                key = KEY,
                sensor = SENSOR
            )
        }

    }
}