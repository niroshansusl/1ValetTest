package com.niroshan.smarthome.ui.devices.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.niroshan.smarthome.data.DeviceList
import com.niroshan.smarthome.databinding.ItemDevicesBinding
import com.niroshan.smarthome.internal.setAvatarImage
import com.niroshan.smarthome.ui.devices.DevicesFragmentDirections

class DeviceListAdapter : RecyclerView.Adapter<DeviceListAdapter.ViewHolder>(), Filterable {

    val deviceListFiltered: ArrayList<DeviceList> = ArrayList()
    private val deviceList: ArrayList<DeviceList> = ArrayList()

    fun updateData(data: ArrayList<DeviceList>) {
        deviceListFiltered.clear()
        this.deviceListFiltered.addAll(data)
        this.deviceList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDevicesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        deviceListFiltered[position].let { device ->
            with(holder) {
                itemView.tag = device
                if (device != null) {
                    bind(createOnClickListener(binding, device), device)
                }
            }
        }
    }

    private fun createOnClickListener(
        binding: ItemDevicesBinding,
        device: DeviceList
    ): View.OnClickListener {
        return View.OnClickListener {
            val directions = DevicesFragmentDirections.actionDevicesToDetails(device)
            val extras = FragmentNavigatorExtras(
                binding.avatar to "avatar_${device.title}"
            )
            it.findNavController().navigate(directions, extras)
        }
    }


    override fun getItemCount(): Int {
        return deviceListFiltered.size
    }

    class ViewHolder(val binding: ItemDevicesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, device: DeviceList) {

            binding.apply {
                device.imageUrl?.let { avatar.setAvatarImage(itemView, it) }
                title.text = device.title
                currency.text = device.currency
                price.text = device.price.toString()

                ViewCompat.setTransitionName(this.avatar, "avatar_${device.title}")

                root.setOnClickListener(listener)
            }
        }
    }

    override fun getFilter(): Filter = (object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filteredList = ArrayList<DeviceList>()
            if (p0.isNullOrEmpty()) {
                filteredList.addAll(deviceList)
            } else {
                filteredList.addAll(deviceList.filter { it.title!!.contains(p0.toString(), true) })
            }

            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            deviceListFiltered.clear()
            deviceListFiltered.addAll(p1?.values as Collection<DeviceList>)
            notifyDataSetChanged()
        }

    })
}