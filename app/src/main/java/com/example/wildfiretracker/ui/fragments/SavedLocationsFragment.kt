package com.example.wildfiretracker.ui.fragments

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wildfiretracker.R
import com.example.wildfiretracker.adapters.SavedLocationAdapter
import com.example.wildfiretracker.databinding.FragmentSavedLocationsBinding
import com.example.wildfiretracker.db.entities.SavedLocation
import com.example.wildfiretracker.services.AlertsService
import com.example.wildfiretracker.ui.viewmodels.SavedLocationsViewModel
import com.example.wildfiretracker.util.Resource
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.util.*

@AndroidEntryPoint
class SavedLocationsFragment : Fragment(R.layout.fragment_saved_locations) {

    private lateinit var savedLocationAdapter: SavedLocationAdapter
    private lateinit var binding: FragmentSavedLocationsBinding
    private val viewModel: SavedLocationsViewModel by viewModels()
    private val TAG = "SavedLocationsFragment"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedLocationsBinding.bind(view)

        setupRecyclerView()
        subscribeToObservers()

        binding.btnAddLocation.setOnClickListener {
            if(binding.etvEnterLocation.text.toString() != ""){
                addLocation()
            }
        }
    }

    private fun sendCommandToService(action: String) =
        Intent(requireContext(), AlertsService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }

    private fun setupRecyclerView() = binding.rvSavedLocations.apply {
        savedLocationAdapter = SavedLocationAdapter()
        adapter = savedLocationAdapter
        layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(this)
    }

    private fun subscribeToObservers() {
        viewModel.savedLocations.observe(viewLifecycleOwner, Observer { savedLocations ->
            viewModel.retrieveSavedLocationsWildFireData()
            savedLocationAdapter.submitList(savedLocations)
            Log.v(TAG, "Called retrieveSavedLocationsWildFireData and submitList")
        })

        viewModel.savedLocationsWildFires.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    Log.v(TAG, "Received SavedLocationsWildFire events successfully")
                }
                is Resource.Error -> {
                    Log.v(TAG, "Error receiving SavedLocationsWildFire events!!!")
                }
                is Resource.Loading -> {
                    Log.v(TAG, "Loading SavedLocationsWildFire events...")
                }
            }
        })
    }

    private fun addLocation() {
        try {
            val city: String = binding.etvEnterLocation.text.toString()
            val gc = Geocoder(requireContext(), Locale.getDefault())
            val addresses: MutableList<Address>? = gc.getFromLocationName(city, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                val address: Address = addresses[0]
                Log.v(TAG, "Latitude: ${address.latitude} Longitude: ${address.longitude}")
                val location = SavedLocation(
                    city,
                    LatLng(address.latitude, address.longitude),
                    sendAlerts = false,
                    hasLiveEvent = false,
                    lastEventLocation = LatLng(address.latitude, address.longitude),
                    lastEventDate = LocalDate.of(1,1,1)
                )
                viewModel.insertSavedLocation(location)
                binding.etvEnterLocation.text.clear()

            }
            else {
                Toast.makeText(requireContext(), "City not found", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.xor(ItemTouchHelper.RIGHT)) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val location = savedLocationAdapter.getSavedLocationAt(viewHolder.adapterPosition)
            viewModel.deleteSavedLocation(location)
            Toast.makeText(
                requireContext(),
                "${location.city} was removed from your Saved Locations",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

}