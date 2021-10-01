package com.frost.ModelMVVM.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class LocalCurrency(
    var v: Double?,
    var name: String?
):Parcelable
