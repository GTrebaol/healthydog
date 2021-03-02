package com.gtreb.healthydog.veterinaire

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.gtreb.healthydog.R
import com.gtreb.healthydog.common.implementation.CustomFragment
import com.gtreb.healthydog.common.navigation.NavigationItem
import com.gtreb.healthydog.databinding.VeterinaireFragmentBinding
import kotlinx.android.synthetic.main.veterinaire_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel

class VeterinaireFragment : CustomFragment<VeterinaireFragmentBinding>(), OnMapReadyCallback {

    private val appCoordinator: VeterinaireCoordinator by inject()
    private lateinit var googleMap: GoogleMap
    private lateinit var mapView: MapView
    private val viewModelVeterinaire: VeterinaireFragmentViewModel by sharedViewModel()
    override val layoutId: Int = R.layout.veterinaire_fragment

    override val navigationItem: NavigationItem
        get() = NavigationItem(VeterinaireModule::class, VeterinaireFragment::class)


    @ExperimentalCoroutinesApi
    override fun bindViewModels(binding: VeterinaireFragmentBinding) {
        binding.viewModelVeterinaire = viewModelVeterinaire
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapView = map
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        val sydney = LatLng(-34.0, 151.0)
        this.googleMap.addMarker(
            MarkerOptions()
            .position(sydney)
            .title("Marker in Sydney"))
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

    }
}