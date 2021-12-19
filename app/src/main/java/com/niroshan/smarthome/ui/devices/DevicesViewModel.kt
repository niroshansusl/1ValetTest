package com.niroshan.smarthome.ui.devices

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niroshan.smarthome.api.RetroInstance
import com.niroshan.smarthome.api.SmartHomeAPI
import com.niroshan.smarthome.api.response.ResultWrapper
import com.niroshan.smarthome.data.DeviceList
import com.niroshan.smarthome.data.State
import com.niroshan.smarthome.internal.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DevicesViewModel : ViewModel() {

    private var deviceListLiveData : MutableLiveData<List<DeviceList>> = MutableLiveData()
    private val retroInstance = RetroInstance.getRetroInstance().create(SmartHomeAPI::class.java)
    var progressState: MutableLiveData<State> = MutableLiveData()

    fun getDeviceListObserver(): MutableLiveData<List<DeviceList>> {
        return deviceListLiveData
    }

    fun makeApiCall(){
        viewModelScope.launch {
            val result = safeApiCall(Dispatchers.IO){
                retroInstance.getDeviceDataFromAPI()
            }

            when (result) {
                is ResultWrapper.NetworkError -> {
                    result.toString()
                    progressState.value = State.ERROR
                }
                is ResultWrapper.GenericError -> {
                    result.code
                    progressState.value = State.ERROR
                }
                is ResultWrapper.Success -> {
                    deviceListLiveData.postValue(result.value)
                    progressState.value = State.DONE
                }
            }
        }
    }

}