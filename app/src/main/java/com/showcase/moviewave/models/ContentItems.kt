package com.showcase.moviewave.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Harshali.Sachani on 9/22/2023.
 */
data class ContentItems(
    @SerializedName("content")
    val content: List<Content>
)
