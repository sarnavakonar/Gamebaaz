package com.example.gamebaaz.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Developer(
    val id: Int,
    val name: String,
    val logo: String,
    val about: String,
    val founded: String,
    val twitter: String,
    val insta: String,
    val fb: String
) : Parcelable
