package com.showcase.movieapp.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Harshali.Sachani on 9/22/2023.
 */
data class Content(
    @SerializedName("name")
    val name: String,
    @SerializedName("poster-image")
    val posterImage: String
)