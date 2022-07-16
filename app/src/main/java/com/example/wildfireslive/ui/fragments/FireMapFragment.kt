package com.example.wildfireslive.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.wildfireslive.R
import com.example.wildfireslive.databinding.FragmentFireMapBinding
import com.example.wildfireslive.ui.viewmodels.FireMapViewModel
import com.google.android.gms.maps.GoogleMap


class FireMapFragment : Fragment(R.layout.fragment_fire_map){
    private lateinit var binding: FragmentFireMapBinding

    private val viewModel: FireMapViewModel by viewModels()
    private val TAG = "FireMapFragment"


    private var map: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFireMapBinding.bind(view)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync {
            map = it
        }
    }



    override fun onResume() {
        super.onResume()
        binding.mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView?.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView?.onDestroy()
    }
}