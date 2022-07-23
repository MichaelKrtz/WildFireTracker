package com.example.wildfireslive.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.wildfireslive.R
import com.example.wildfireslive.databinding.FragmentFireMapBinding
import com.example.wildfireslive.network.eonetapi.Event
import com.example.wildfireslive.network.eonetapi.WildFiresResponse
import com.example.wildfireslive.ui.viewmodels.FireMapViewModel
import com.example.wildfireslive.util.Resource
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class FireMapFragment : Fragment(R.layout.fragment_fire_map), OnMapReadyCallback{
    private lateinit var binding: FragmentFireMapBinding

    private val viewModel: FireMapViewModel by viewModels()
    private val TAG = "FireMapFragment"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFireMapBinding.bind(view)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync {
            onMapReady(it)
        }


    }

    override fun onMapReady(googleMap: GoogleMap) {
        viewModel.wildFires.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    response.data?.let { wildFiresResponse ->
                        addMarkersOnMap(googleMap, wildFiresResponse)
                    }
                    Log.v(TAG, "Received WildFire events successfully")
                }
                is Resource.Error -> {
                    Log.v(TAG, "Error receiving WildFire events!!!")
                }
                is Resource.Loading -> {
                    Log.v(TAG, "Loading WildFire events...")
                }
            }

        })

    }

    private fun addMarkersOnMap(googleMap: GoogleMap, wildFiresResponse: WildFiresResponse) {
        for (event in wildFiresResponse.events) {
            googleMap.addMarker(
                MarkerOptions()
                    .position(getEventLocation(event))
                    .title(event.title)
                    .snippet(event.geometry[0].date)
            )
        }

        val krini = LatLng(40.583339306297304, 22.938709193209924)
        googleMap.addMarker(
            MarkerOptions()
                .position(krini)
                .title("Marker in Krini")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(krini))
    }

    private fun getEventLocation(event: Event): LatLng {
        val lat = event.geometry[0].coordinates[1]
        val lng = event.geometry[0].coordinates[0]
        return LatLng(lat, lng)
    }


    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }
}