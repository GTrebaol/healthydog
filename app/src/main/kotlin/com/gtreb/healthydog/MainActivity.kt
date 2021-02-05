package com.gtreb.healthydog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gtreb.healthydog.common.interfaces.IRouter
import org.koin.android.ext.android.inject
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

class MainActivity : AppCompatActivity() {

    private val router: IRouter by inject()
    private val coordinator: AppCoordinator by inject()



    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)
    }
}