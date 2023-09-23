package com.showcase.movieapp.utils

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable

/**
 * Created by Harshali.Sachani on 9/23/2023.
 */

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

fun Activity.getGridSpan(): Int {
    return when (resources.configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> 6
        Configuration.ORIENTATION_PORTRAIT -> 3
        else -> 3
    }
}