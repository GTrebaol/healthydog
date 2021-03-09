package com.gtreb.healthydog.veterinary

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
import com.gtreb.healthydog.databinding.VeterinaryFragmentBinding
import com.gtreb.healthydog.model.VeterinaryPlace
import com.gtreb.healthydog.utils.Constants.DEFAULT_ZOOM
import com.gtreb.healthydog.utils.Constants.KEY_LOCATION
import com.gtreb.healthydog.utils.bitmapDescriptorFromVector
import kotlinx.android.synthetic.main.veterinary_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.sharedViewModel

/**
 * This fragment will display the nearest veterinary offices around the user
 * It will ask for GPS permission, fetch the nearby places and display them
 */
class VeterinaryFragment : CustomFragment<VeterinaryFragmentBinding>(), OnMapReadyCallback,
    LocationListener {

    private lateinit var mapView: MapView
    private lateinit var permissionHandler: ActivityResultLauncher<String>
    private lateinit var locationManager: LocationManager
    private lateinit var placesClient: PlacesClient
    private lateinit var observer: androidx.lifecycle.Observer<List<VeterinaryPlace>>

    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var locationPermissionGranted = false

    private val viewModelVeterinary: VeterinaryFragmentViewModel by sharedViewModel()
    override val layoutId: Int = R.layout.veterinary_fragment

    override val navigationItem: NavigationItem
        get() = NavigationItem(VeterinaryModule::class, VeterinaryFragment::class)

    @ExperimentalCoroutinesApi
    override fun bindViewModels(binding: VeterinaryFragmentBinding) {
        binding.viewModelVeterinaire = viewModelVeterinary
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (null != savedInstanceState) {
            viewModelVeterinary.lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)!!
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
        updateVisibility(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        mapView = map
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        viewModelVeterinary.googleMap = googleMap
        viewModelVeterinary.googleMap.uiSettings.isZoomControlsEnabled = true
        viewModelVeterinary.googleMap.uiSettings.isTiltGesturesEnabled = true
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


    /**
     * Get the user last known location, if it doesn't exists, ask for the current location
     */
    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        if (locationPermissionGranted) {
            val locationResult =
                LocationServices.getFusedLocationProviderClient(requireActivity()).lastLocation
            locationResult.addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Set the map's camera position to the current location of the device.
                    if (task.result != null) {
                        viewModelVeterinary.lastKnownLocation = task.result
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
                    viewModelVeterinary.googleMap.moveCamera(
                        CameraUpdateFactory
                            .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
                    )
                    viewModelVeterinary.googleMap.uiSettings?.isMyLocationButtonEnabled = false
                }
            }
        }

    }

    override fun onLocationChanged(location: Location) {
        viewModelVeterinary.lastKnownLocation = location
        updatePosition()
    }

    private fun updatePosition() {
        val position = LatLng(
            viewModelVeterinary.lastKnownLocation.latitude,
            viewModelVeterinary.lastKnownLocation.longitude
        )
        viewModelVeterinary.load()
        viewModelVeterinary.googleMap.addMarker(
            MarkerOptions()
                .position(position)
                .title("Your position")
                .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_nerd))
        )
    }

    private fun addMarkerForVet(veterinaryPlace: VeterinaryPlace) {
        val position =
            LatLng(veterinaryPlace.geometry.location.lat, veterinaryPlace.geometry.location.lng)
        viewModelVeterinary.googleMap.addMarker(
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
        updateVisibility(false)
    }

    override fun onProviderEnabled(provider: String) {
        updateVisibility(true)
    }


    private fun updateVisibility(providerEnabled: Boolean) {
        gpsNotOn.visibility = if (providerEnabled) View.GONE else View.VISIBLE
        mapAndList.visibility = if (!providerEnabled) View.GONE else View.VISIBLE
    }
}