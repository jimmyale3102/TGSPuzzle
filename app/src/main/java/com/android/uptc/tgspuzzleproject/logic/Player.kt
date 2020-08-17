package com.android.uptc.tgspuzzleproject.logic

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player(
    val username: String,
    val score: String): Parcelable {
    constructor(): this(
        username = "",
        score = "")
}