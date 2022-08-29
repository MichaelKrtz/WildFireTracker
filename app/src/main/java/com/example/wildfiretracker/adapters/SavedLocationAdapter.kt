package com.example.wildfiretracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wildfiretracker.R
import com.example.wildfiretracker.databinding.ItemSavedLocationBinding
import com.example.wildfiretracker.db.entities.SavedLocation

class SavedLocationAdapter(
) : RecyclerView.Adapter<SavedLocationAdapter.SavedLocationViewHolder>(){


    private val diffCallback = object : DiffUtil.ItemCallback<SavedLocation>() {
        override fun areItemsTheSame(oldItem: SavedLocation, newItem: SavedLocation): Boolean {
            return oldItem.city == newItem.city
        }

        override fun areContentsTheSame(oldItem: SavedLocation, newItem: SavedLocation): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<SavedLocation>) {
        return differ.submitList(list)
    }

    fun getSavedLocationAt(pos: Int): SavedLocation {
        return differ.currentList[pos]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedLocationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_location, parent, false)
        return SavedLocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedLocationViewHolder, position: Int) {
        val location = differ.currentList[position]
        val binding: ItemSavedLocationBinding = ItemSavedLocationBinding.bind(holder.itemView)
        holder.itemView.apply {
            binding.apply {
                tvCity.text = location.city
                tvLocation.text = location.lastEventLocation.toString()
                tvDate.text = location.lastEventDate.toString()
                if (location.sendAlerts) switchAlerts.isChecked = true
                if (location.hasLiveEvent) {
                    tvLive.highlightColor
                    ivFire.visibility = View.VISIBLE
                    tvLive.visibility = View.VISIBLE
                }
            }
        }
    }
    

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class SavedLocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}