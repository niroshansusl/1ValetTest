package com.niroshan.smarthome.ui.devices

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.niroshan.smarthome.R
import com.niroshan.smarthome.data.DeviceList
import com.niroshan.smarthome.data.State
import com.niroshan.smarthome.databinding.FragmentDevicesBinding
import com.niroshan.smarthome.ui.devices.adapter.DeviceListAdapter
import kotlinx.android.synthetic.main.fragment_devices.*
import kotlin.collections.ArrayList

class DevicesFragment : Fragment(R.layout.fragment_devices) {

    private val viewModel by viewModels<DevicesViewModel>()

    private lateinit var adapterDevice: DeviceListAdapter

    private var _binding: FragmentDevicesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)
        _binding = FragmentDevicesBinding.bind(view)
        initView()
        initViewModel()
    }

    private fun initView(){

        adapterDevice = DeviceListAdapter()

        binding.apply {

            recycler.apply {
                layoutManager = LinearLayoutManager(activity)
                setHasFixedSize(true)
                itemAnimator = null
                adapter = adapterDevice
            }

            btnRetry.setOnClickListener {
                viewModel.makeApiCall()
            }
        }

    }

    private fun initViewModel(){

        viewModel.getDeviceListObserver().observe(viewLifecycleOwner, { device ->
            device?.let { adapterDevice.updateData(device as ArrayList<DeviceList>) }
            adapterDevice.notifyDataSetChanged()
        })

        viewModel.progressState.observe(viewLifecycleOwner, {   state ->
            when (state) {
                State.DONE -> {
                    progress.visibility = View.GONE
                }
                State.ERROR -> {
                    progress.visibility = View.GONE
                }
                State.LOADING -> {
                    progress.visibility = View.VISIBLE
                }
            }

        })

        viewModel.makeApiCall()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_filter, menu)

        val searchItem = menu.findItem(R.id.action_filter)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapterDevice.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterDevice.filter.filter(newText)
                return true
            }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}