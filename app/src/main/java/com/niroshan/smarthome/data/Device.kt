package com.niroshan.smarthome.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DeviceList(
    val id: Int?,
    val type: String?,
    val price: Int?,
    val currency: String?,
    val title: String?,
    val imageUrl: String?,
    val description: String?
) : Parcelable