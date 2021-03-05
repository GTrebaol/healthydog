package com.gtreb.healthydog.veterinaire

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.gtreb.healthydog.BuildConfig
import com.gtreb.healthydog.R
import com.gtreb.healthydog.common.implementation.CustomFragment
import com.gtreb.healthydog.common.implementation.PermissionHandler
import com.gtreb.healthydog.common.implementation.handlePermission
import com.gtreb.healthydog.common.implementation.requestPermission
import com.gtreb.healthydog.common.navigation.NavigationItem
import com.gtreb.healthydog.databinding.VeterinaireFragmentBinding
import com.gtreb.healthydog.utils.Constants.DEFAULT_ZOOM
import com.gtreb.healthydog.utils.Constants.KEY_LOCATION
import kotlinx.android.synthetic.main.veterinaire_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.sharedViewModel


class VeterinaireFragment : CustomFragment<VeterinaireFragmentBinding>(), OnMapReadyCallback,
    LocationListener {

    private lateinit var googleMap: GoogleMap
    private lateinit var mapView: MapView
    private lateinit var lastKnownLocation: Location
    private lateinit var permissionHandler: ActivityResultLauncher<String>
    private lateinit var locationManager: LocationManager
    private lateinit var placesClient: PlacesClient
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var locationPermissionGranted = false

    private val viewModelVeterinaire: VeterinaireFragmentViewModel by sharedViewModel()
    override val layoutId: Int = R.layout.veterinaire_fragment

    override val navigationItem: NavigationItem
        get() = NavigationItem(VeterinaireModule::class, VeterinaireFragment::class)

    @ExperimentalCoroutinesApi
    override fun bindViewModels(binding: VeterinaireFragmentBinding) {
        binding.viewModelVeterinaire = viewModelVeterinaire
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)!!
        }
        Places.initialize(requireContext(), BuildConfig.API_KEY)
        placesClient = Places.createClient(requireContext())
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        permissionHandler = requestPermission(PermissionHandler.AccessFineLocation)
        initPermissionsHandler()
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = map
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)

        viewModelVeterinaire.load(5)
        viewModelVeterinaire.vetLocations.observe(lifecycleOwner) {

        }
    }

    private fun initPermissionsHandler() {
        handlePermission(PermissionHandler.AccessFineLocation,
            onGranted = {
                locationPermissionGranted = true
                startLocationManager()

            },
            onDenied = {

            },
            onRationaleNeeded = {

            })
        permissionHandler.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationManager() {
        locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        this.googleMap.uiSettings.isZoomControlsEnabled = true
        getCurrentLocation()
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val locationResult =
            LocationServices.getFusedLocationProviderClient(requireActivity()).lastLocation
        locationResult.addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                // Set the map's camera position to the current location of the device.
                if (task.result != null) {
                    lastKnownLocation = task.result
                    updatePosition()
                }
            } else {
                logger.logE("Current location is null. Using defaults.")
                logger.logE("Exception: %s", task.exception)
                googleMap.moveCamera(
                    CameraUpdateFactory
                        .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
                )
                googleMap.uiSettings?.isMyLocationButtonEnabled = false
            }
        }
    }

    override fun onLocationChanged(location: Location) {
        this.lastKnownLocation = location
        this.updatePosition()
    }

    private fun updatePosition() {
        val position = LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
        this.googleMap.addMarker(
            MarkerOptions()
                .position(position)
                .title("Your position")
        )
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    lastKnownLocation.latitude,
                    lastKnownLocation.longitude
                ), DEFAULT_ZOOM.toFloat()
            )
        )
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(position))
    }

}