package com.showcase.movieapp.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.showcase.movieapp.R
import com.showcase.movieapp.databinding.ItemMovieBinding
import com.showcase.movieapp.models.Content

/**
 * Created by Harshali.Sachani on 9/22/2023.
 */
class MovieAdapter(private val mActivity: Activity, private var movieList: ArrayList<Content>) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    fun updatePageItems(newPageList: List<Content>) {
        movieList.addAll(newPageList)
    }

    override fun getItemCount() = movieList.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.setData(movieList[position])
    }

    inner class MovieHolder(private val binding: ItemMovieBinding) : ViewHolder(binding.root) {

        fun setData(content: Content) {
            binding.txtMovieName.text = content.name
            binding.txtMovieName.isSelected = true

            with(content.posterImage.split(".")) {
                val res = if (isNotEmpty()) {
                    mActivity.resources.getIdentifier(this[0], "drawable", mActivity.packageName)
                } else R.drawable.placeholder_for_missing_posters
                Glide.with(binding.imgMoviePoster)
                    .load(res)
                    .error(R.drawable.placeholder_for_missing_posters)
                    .into(binding.imgMoviePoster)
            }

        }

    }


}