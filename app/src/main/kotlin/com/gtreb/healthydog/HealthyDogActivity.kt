package com.gtreb.healthydog

import android.os.Bundle
import com.gtreb.healthydog.common.interfaces.IRouter
import com.gtreb.healthydog.common.navigation.NavigationListener
import com.gtreb.healthydog.common.navigation.logic.HealthyDogActivityViewModel
import com.gtreb.healthydog.common.presentation.CustomActivity
import com.gtreb.healthydog.databinding.HealthyDogActivityBinding
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

class HealthyDogActivity : CustomActivity<HealthyDogActivityBinding>() {

    private val router: IRouter by inject()
    private val coordinator: AppCoordinator by inject()
    private val navigationListener by lazy { NavigationListener(this) }
    private val viewModel: HealthyDogActivityViewModel by viewModel()
    override val layoutId: Int = R.layout.healthy_dog_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setupKoinFragmentFactory()
        lifecycle.addObserver(navigationListener)
        navigationListener.displayed.observe(
            this,
            {
                println("Navigation : ${it.toList().joinToString { "${it.first} - ${it.second}" }}")
            }
        )
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

    override fun bindViewModels(binding: HealthyDogActivityBinding) {
        binding.viewModel = viewModel
    }
}