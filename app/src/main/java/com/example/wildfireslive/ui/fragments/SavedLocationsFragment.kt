package com.example.wildfireslive.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wildfireslive.R
import com.example.wildfireslive.adapters.SavedLocationAdapter
import com.example.wildfireslive.databinding.FragmentSavedLocationsBinding
import com.example.wildfireslive.db.SavedLocation
import com.example.wildfireslive.ui.viewmodels.FireMapViewModel
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDate

class SavedLocationsFragment : Fragment(R.layout.fragment_saved_locations) {


    private lateinit var binding: FragmentSavedLocationsBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedLocationsBinding.bind(view)




        val locs = mutableListOf<SavedLocation>()
        val myDate = LocalDate.parse("2022-07-15")
        val sl1: SavedLocation = SavedLocation("Thessaloniki",
            LatLng(50.00,50.00),
            myDate,
            sendAlerts = true,
            hasLiveEvent = false
        )
        val myDate2 = LocalDate.parse("2020-07-15")
        val sl2: SavedLocation = SavedLocation("Larissa",
            LatLng(70.00,70.00),
            myDate,
            sendAlerts = false,
            hasLiveEvent = true
        )
        val sl3: SavedLocation = SavedLocation("Thessaloniki",
            LatLng(50.00,50.00),
            myDate,
            sendAlerts = true,
            hasLiveEvent = false
        )
        val sl4: SavedLocation = SavedLocation("Larissa",
            LatLng(70.00,70.00),
            myDate,
            sendAlerts = false,
            hasLiveEvent = true
        )
        val sl5: SavedLocation = SavedLocation("Thessaloniki",
            LatLng(50.00,50.00),
            myDate,
            sendAlerts = true,
            hasLiveEvent = false
        )
        val sl6: SavedLocation = SavedLocation("Larissa",
            LatLng(70.00,70.00),
            myDate,
            sendAlerts = false,
            hasLiveEvent = true
        )


        locs.addAll(listOf(sl1, sl2, sl3, sl4, sl5, sl6))

        val adapter = SavedLocationAdapter(locs)
        binding.rvSavedLocations.adapter = adapter
        binding.rvSavedLocations.layoutManager = LinearLayoutManager(activity)
    }
}