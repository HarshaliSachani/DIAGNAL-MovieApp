package com.showcase.moviewave.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.showcase.moviewave.models.Content
import com.showcase.moviewave.models.MovieData
import com.showcase.moviewave.utils_base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Created by Harshali.Sachani on 9/22/2023.
 */
class DashboardViewModel : BaseViewModel() {

    private val movieDataResponse = MutableLiveData<MovieData>()
    val movieData: LiveData<MovieData> = movieDataResponse

    private val searchMovieDataResponse = MutableLiveData<List<Content>>()
    val searchMovieData: LiveData<List<Content>> = searchMovieDataResponse

    fun getMovieDataFromAsset(context: Context, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val jsonString = context.readJsonPage(page)
            movieDataResponse.postValue(Gson().fromJson(jsonString, MovieData::class.java))
        }
    }

    fun searchMovies(context: Context, searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val totalMovies = ArrayList<Content>()
            for (i in 0 until 3) {
                val jsonString = context.readJsonPage(i + 1)
                totalMovies.addAll(Gson().fromJson(jsonString, MovieData::class.java).page.contentItems.content)
            }
            searchMovieDataResponse.postValue(totalMovies.filter { it.name.contains(searchQuery, true) })
        }

    }

}

fun Context.readJsonPage(pageNo: Int): String {
    return try {
        assets.open("CONTENTLISTINGPAGE-PAGE$pageNo.json").bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ""
    }
}