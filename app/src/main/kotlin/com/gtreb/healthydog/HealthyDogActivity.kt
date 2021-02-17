package com.gtreb.healthydog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.gtreb.healthydog.common.interfaces.IRouter
import com.gtreb.healthydog.common.navigation.NavigationListener
import org.koin.android.ext.android.inject
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

class HealthyDogActivity : AppCompatActivity() {

    private val router: IRouter by inject()
    private val coordinator: AppCoordinator by inject()
    private val navigationListener by lazy { NavigationListener(this) }
    private val isLoading: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupKoinFragmentFactory()
        setContentView(R.layout.activity_main)
        lifecycle.addObserver(navigationListener)
        navigationListener.displayed.observe(
            this,
            Observer {
                println("Navigation : ${it.toList().joinToString { "${it.first} - ${it.second}" }}")
            }
        )
        // Init router coordinator with fragment manager and fragment's container
        router.init(
            mapOf(
                IRouter.FRAGMENT_MANAGER to supportFragmentManager,
                IRouter.CONTAINER_ID to R.id.container
            )
        )
    }

    override fun onResume() {
        super.onResume()
        if (router.isEmpty()) {
            coordinator.startMain()
        }
    }

    override fun onBackPressed() {
        router.doBack()
        if (router.isEmpty()) super.onBackPressed()
    }
}