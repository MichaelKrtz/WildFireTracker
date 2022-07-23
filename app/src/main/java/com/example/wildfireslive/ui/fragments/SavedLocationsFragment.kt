package com.example.wildfireslive.ui.fragments

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wildfireslive.R
import com.example.wildfireslive.adapters.SavedLocationAdapter
import com.example.wildfireslive.databinding.FragmentSavedLocationsBinding
import com.example.wildfireslive.db.entities.SavedLocation
import com.example.wildfireslive.ui.viewmodels.SavedLocationsViewModel
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDate
import java.util.*

class SavedLocationsFragment : Fragment(R.layout.fragment_saved_locations) {

    private lateinit var savedLocationAdapter: SavedLocationAdapter
    private lateinit var binding: FragmentSavedLocationsBinding
    private lateinit var viewModel: SavedLocationsViewModel
    private val TAG = "SavedLocationsFragment"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedLocationsBinding.bind(view)
        val mContext = requireContext()
        viewModel = SavedLocationsViewModel(mContext)
        setupRecyclerView()

        viewModel.savedLocations.observe(viewLifecycleOwner, Observer {
            it?.let { savedLocationAdapter.submitList(it) }
            Log.v(TAG, viewModel.savedLocations.value.toString())
        })
        
        binding.btnAddLocation.setOnClickListener {
            if(binding.etvEnterLocation.text.toString() != ""){
                addLocation()
            }
            savedLocationAdapter.notifyDataSetChanged()
        }
    }

    private fun setupRecyclerView() = binding.rvSavedLocations.apply {
        savedLocationAdapter = SavedLocationAdapter()
        adapter = savedLocationAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun addLocation() {
        val city: String = binding.etvEnterLocation.text.toString()
        val gc = Geocoder(requireContext(), Locale.getDefault())
        val addresses: MutableList<Address>? = gc.getFromLocationName(city, 1)
        val address: Address? = addresses?.get(0)

        if (address != null) {
            Log.v(TAG, "Latitude: ${address.latitude} Longitude: ${address.longitude}")
            val location = SavedLocation(
                city, LatLng(address.latitude, address.longitude), LocalDate.now(),
                sendAlerts = false,
                hasLiveEvent = false)
            viewModel.insertSavedLocation(location)
        } else {
            Log.v(TAG, "Location not found")
        }
    }

}