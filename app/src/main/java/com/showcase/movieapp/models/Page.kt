package com.showcase.movieapp.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Harshali.Sachani on 9/22/2023.
 */
data class Page(
    @SerializedName("content-items")
    val contentItems: ContentItems,
    @SerializedName("page-num")
    val pageNum: String,
    @SerializedName("page-size")
    val pageSize: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("total-content-items")
    val totalContentItems: String,
)