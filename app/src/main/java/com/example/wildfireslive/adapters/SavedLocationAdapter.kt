package com.example.wildfireslive.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wildfireslive.R
import com.example.wildfireslive.databinding.ItemSavedLocationBinding
import com.example.wildfireslive.db.SavedLocation

class SavedLocationAdapter(
    var savedLocations: List<SavedLocation>
) : RecyclerView.Adapter<SavedLocationAdapter.SavedLocationViewHolder>(){





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedLocationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_location, parent, false)
        return SavedLocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedLocationViewHolder, position: Int) {
        var binding: ItemSavedLocationBinding = ItemSavedLocationBinding.bind(holder.itemView)
        holder.itemView.apply {
            binding.apply {
                tvCity.text = savedLocations[position].city
                tvLocation.text = savedLocations[position].location.toString()
                tvDate.text = savedLocations[position].date.toString()
                if (savedLocations[position].sendAlerts) switchAlerts.isChecked = true
                if (savedLocations[position].hasLiveEvent) {
                    tvLive.highlightColor
                    ivFire.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return savedLocations.size
    }

    inner class SavedLocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}