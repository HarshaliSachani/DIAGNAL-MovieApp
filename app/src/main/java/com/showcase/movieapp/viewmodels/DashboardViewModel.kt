package com.showcase.movieapp.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.showcase.movieapp.models.MovieData
import com.showcase.movieapp.utils_base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Created by Harshali.Sachani on 9/22/2023.
 */
class DashboardViewModel : BaseViewModel() {

    private val movieDataResponse = MutableLiveData<MovieData>()
    val movie: LiveData<MovieData> get() = movieDataResponse

    fun getMovieDataFromAsset(context: Context, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val jsonString = try {
                context.assets.open("CONTENTLISTINGPAGE-PAGE$page.json").bufferedReader().use { it.readText() }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                ""
            }
            movieDataResponse.postValue(Gson().fromJson(jsonString, MovieData::class.java))
        }
    }

}