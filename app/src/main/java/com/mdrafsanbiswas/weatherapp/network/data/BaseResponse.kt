package com.mdrafsanbiswas.weatherapp.network.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
open class BaseResponse(
    @SerializedName("success") val success: Boolean? = null,
    @SerializedName("code") val code: Int? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("message") val message: String? = null
) : Parcelable
