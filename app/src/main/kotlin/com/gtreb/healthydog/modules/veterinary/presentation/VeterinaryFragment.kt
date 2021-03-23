package com.gtreb.healthydog.modules.veterinary.presentation

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.gtreb.healthydog.R
import com.gtreb.healthydog.common.implementations.PermissionHandler
import com.gtreb.healthydog.common.implementations.handlePermission
import com.gtreb.healthydog.common.implementations.requestPermission
import com.gtreb.healthydog.common.navigation.NavigationItem
import com.gtreb.healthydog.common.presentation.CustomFragment
import com.gtreb.healthydog.databinding.VeterinaryFragmentBinding
import com.gtreb.healthydog.modules.veterinary.VeterinaryModule
import com.gtreb.healthydog.modules.veterinary.domain.VeterinaryPlace
import com.gtreb.healthydog.utils.SettingsAction
import kotlinx.android.synthetic.main.veterinary_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.sharedViewModel

/**
 * This fragment will display the nearest veterinary offices around the user
 * It will ask for GPS permission, fetch the nearby places and display them
 */
class VeterinaryFragment : CustomFragment<VeterinaryFragmentBinding>(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var permissionHandler: ActivityResultLauncher<String>
    private lateinit var observer: androidx.lifecycle.Observer<List<VeterinaryPlace>>

    private val viewModelVeterinary: VeterinaryFragmentViewModel by sharedViewModel()
    override val layoutId: Int = R.layout.veterinary_fragment

    override val navigationItem: NavigationItem
        get() = NavigationItem(VeterinaryModule::class, VeterinaryFragment::class)

    @ExperimentalCoroutinesApi
    override fun bindViewModels(binding: VeterinaryFragmentBinding) {
        binding.viewModelVeterinaire = viewModelVeterinary
    }


    override fun onAttach(context: Context) {
        permissionHandler = requestPermission(PermissionHandler.AccessFineLocation)
        initPermissionsHandler()
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelVeterinary.updateVisibility()
        mapView = map
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)


        viewModelVeterinary.goToSettings.observe(lifecycleOwner) {
            try {
                requireActivity().runOnUiThread {
                    startActivity(Intent(SettingsAction.GPS.value))
                }
            } catch (e: ActivityNotFoundException) {
                logger.logE(e.message.toString(), e)
            }

        }
    }

    private fun initPermissionsHandler() {
        handlePermission(
            PermissionHandler.AccessFineLocation,
            onGranted = {
                viewModelVeterinary.locationPermissionGranted = true
                viewModelVeterinary.startLocationManager()
            },
            onDenied = {},
            onRationaleNeeded = {})
        permissionHandler.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        viewModelVeterinary.googleMap = googleMap
        viewModelVeterinary.googleMap.uiSettings.isZoomControlsEnabled = true
        viewModelVeterinary.googleMap.uiSettings.isTiltGesturesEnabled = true
        addObserver()
        viewModelVeterinary.getCurrentLocation()

    }

    override fun onPause() {
        removeObserver()
        super.onPause()
    }


    private fun addObserver() {
        observer = androidx.lifecycle.Observer {
            viewModelVeterinary.vetLocations.value?.forEach {
                viewModelVeterinary.addMarkerForVet(it)
            }
        }
        viewModelVeterinary.vetLocations.observe(lifecycleOwner, observer)
    }

    private fun removeObserver() {
        if (viewModelVeterinary.vetLocations.hasActiveObservers()) {
            viewModelVeterinary.vetLocations.removeObserver(observer)
        }
    }
}