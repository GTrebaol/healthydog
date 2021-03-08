package com.gtreb.healthydog.veterinaire

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
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
import com.gtreb.healthydog.model.VeterinaryPlace
import com.gtreb.healthydog.utils.Constants.DEFAULT_RADIUS
import com.gtreb.healthydog.utils.Constants.DEFAULT_ZOOM
import com.gtreb.healthydog.utils.Constants.KEY_LOCATION
import kotlinx.android.synthetic.main.veterinaire_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.sharedViewModel


class VeterinaireFragment : CustomFragment<VeterinaireFragmentBinding>(), OnMapReadyCallback,
    LocationListener {

    private lateinit var mapView: MapView
    private lateinit var lastKnownLocation: Location
    private lateinit var permissionHandler: ActivityResultLauncher<String>
    private lateinit var locationManager: LocationManager
    private lateinit var placesClient: PlacesClient
    private lateinit var clientMarker: Marker

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

        viewModelVeterinaire.vetLocations.observe(lifecycleOwner) {
            viewModelVeterinaire.vetLocations.value?.forEach {
                addMarkerForVet(it)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.viewModelVeterinaire.googleMap = googleMap
        this.viewModelVeterinaire.googleMap.uiSettings.isZoomControlsEnabled = true
        this.viewModelVeterinaire.googleMap.uiSettings.isTiltGesturesEnabled = true
        getCurrentLocation()
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

    @SuppressLint("MissingPermission")
    private fun startLocationManager() {
        locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
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
                this.viewModelVeterinaire.googleMap.moveCamera(
                    CameraUpdateFactory
                        .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
                )
                this.viewModelVeterinaire.googleMap.uiSettings?.isMyLocationButtonEnabled = false
            }
        }
    }

    override fun onLocationChanged(location: Location) {
        this.lastKnownLocation = location
        this.updatePosition()
    }

    private fun updatePosition() {
        val position = LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
        viewModelVeterinaire.load(lastKnownLocation, DEFAULT_RADIUS)
        clientMarker = this.viewModelVeterinaire.googleMap.addMarker(
            MarkerOptions()
                .position(position)
                .title("Your position")
                .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_nerd))
        )
        this.viewModelVeterinaire.googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    lastKnownLocation.latitude,
                    lastKnownLocation.longitude
                ), DEFAULT_ZOOM.toFloat()
            )
        )
        this.viewModelVeterinaire.googleMap.moveCamera(CameraUpdateFactory.newLatLng(position))
    }

    private fun addMarkerForVet(it: VeterinaryPlace) {
        val position = LatLng(it.geometry.location.lat, it.geometry.location.lng)
        this.viewModelVeterinaire.googleMap.addMarker(
            MarkerOptions()
                .position(position)
                .title(it.name)
                .snippet(it.vicinity)
                .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_veterinarian))
        )
    }

    private fun bitmapDescriptorFromVector(
        context: Context,
        @DrawableRes vectorDrawableResourceId: Int
    ): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId)
        vectorDrawable!!.setBounds(
            0,
            0,
            80,
            80
        )
        val bitmap = Bitmap.createBitmap(
            80,
            80,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}