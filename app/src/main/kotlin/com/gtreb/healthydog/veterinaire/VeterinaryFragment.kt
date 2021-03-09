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
import com.google.android.gms.maps.model.Marker
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
import com.gtreb.healthydog.databinding.VeterinaryFragmentBinding
import com.gtreb.healthydog.model.VeterinaryPlace
import com.gtreb.healthydog.utils.Constants.DEFAULT_RADIUS
import com.gtreb.healthydog.utils.Constants.DEFAULT_ZOOM
import com.gtreb.healthydog.utils.Constants.KEY_LOCATION
import com.gtreb.healthydog.utils.SettingsAction
import com.gtreb.healthydog.utils.bitmapDescriptorFromVector
import kotlinx.android.synthetic.main.veterinary_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.sharedViewModel


class VeterinaryFragment : CustomFragment<VeterinaryFragmentBinding>(), OnMapReadyCallback,
    LocationListener {

    private lateinit var mapView: MapView
    private lateinit var lastKnownLocation: Location
    private lateinit var permissionHandler: ActivityResultLauncher<String>
    private lateinit var locationManager: LocationManager
    private lateinit var placesClient: PlacesClient
    private lateinit var clientMarker: Marker
    private lateinit var observer: androidx.lifecycle.Observer<List<VeterinaryPlace>>

    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var locationPermissionGranted = false
    private var isGpsOn = false

    private val viewModelVeterinary: VeterinaryFragmentViewModel by sharedViewModel()
    override val layoutId: Int = R.layout.veterinary_fragment

    override val navigationItem: NavigationItem
        get() = NavigationItem(VeterinaryModule::class, VeterinaryFragment::class)

    @ExperimentalCoroutinesApi
    override fun bindViewModels(binding: VeterinaryFragmentBinding) {
        binding.viewModelVeterinaire = viewModelVeterinary
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
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.viewModelVeterinary.googleMap = googleMap
        this.viewModelVeterinary.googleMap.uiSettings.isZoomControlsEnabled = true
        this.viewModelVeterinary.googleMap.uiSettings.isTiltGesturesEnabled = true
        addObserver()
        getCurrentLocation()
    }

    override fun onPause() {
        removeObserver()
        super.onPause()
    }

    private fun initPermissionsHandler() {
        handlePermission(PermissionHandler.AccessFineLocation,
            onGranted = {
                locationPermissionGranted = true
                startLocationManager()
            },
            onDenied = {},
            onRationaleNeeded = {})
        permissionHandler.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun startLocationManager() {
        locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        if (locationPermissionGranted) {
            val locationResult =
                LocationServices.getFusedLocationProviderClient(requireActivity()).lastLocation
            locationResult.addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Set the map's camera position to the current location of the device.
                    if (task.result != null) {
                        lastKnownLocation = task.result
                        updatePosition()
                    } else {
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            0L,
                            0f,
                            this
                        )
                    }
                } else {
                    logger.logE("Current location is null. Using defaults.")
                    logger.logE("Exception: %s", task.exception)
                    this.viewModelVeterinary.googleMap.moveCamera(
                        CameraUpdateFactory
                            .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
                    )
                    this.viewModelVeterinary.googleMap.uiSettings?.isMyLocationButtonEnabled = false
                }
            }
        }

    }

    override fun onLocationChanged(location: Location) {
        this.lastKnownLocation = location
        this.updatePosition()
    }

    private fun updatePosition() {
        val position = LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
        viewModelVeterinary.load(lastKnownLocation, DEFAULT_RADIUS)
        clientMarker = this.viewModelVeterinary.googleMap.addMarker(
            MarkerOptions()
                .position(position)
                .title("Your position")
                .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_nerd))
        )
        this.viewModelVeterinary.googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    lastKnownLocation.latitude,
                    lastKnownLocation.longitude
                ), DEFAULT_ZOOM.toFloat()
            )
        )
        this.viewModelVeterinary.googleMap.moveCamera(CameraUpdateFactory.newLatLng(position))
    }

    private fun addMarkerForVet(veterinaryPlace: VeterinaryPlace) {
        val position =
            LatLng(veterinaryPlace.geometry.location.lat, veterinaryPlace.geometry.location.lng)
        this.viewModelVeterinary.googleMap.addMarker(
            MarkerOptions()
                .position(position)
                .title(veterinaryPlace.name)
                .snippet(veterinaryPlace.vicinity)
                .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_veterinarian))
        )
    }

    private fun addObserver() {
        observer = androidx.lifecycle.Observer {
            viewModelVeterinary.vetLocations.value?.forEach {
                addMarkerForVet(it)
            }
        }
        viewModelVeterinary.vetLocations.observe(lifecycleOwner, observer)
    }

    private fun removeObserver() {
        if (viewModelVeterinary.vetLocations.hasActiveObservers()) {
            viewModelVeterinary.vetLocations.removeObserver(observer)
        }
    }

    override fun onProviderDisabled(provider: String) {
        isGpsOn = false
        goSettings(SettingsAction.GPS)
    }

    override fun onProviderEnabled(provider: String) {
        isGpsOn = true
    }
}