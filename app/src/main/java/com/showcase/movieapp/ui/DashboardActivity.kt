package com.showcase.movieapp.ui

import android.animation.Animator
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.showcase.movieapp.R
import com.showcase.movieapp.adapters.MovieAdapter
import com.showcase.movieapp.databinding.ActivityDashboardBinding
import com.showcase.movieapp.models.Content
import com.showcase.movieapp.utils.getGridSpan
import com.showcase.movieapp.utils.parcelable
import com.showcase.movieapp.utils_base.BaseActivity
import com.showcase.movieapp.viewmodels.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.ceil
import kotlin.system.exitProcess


/**
 * Created by Harshali.Sachani on 9/22/2023.
 */
class DashboardActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDashboardBinding
    private val dashboardViewModel: DashboardViewModel by viewModel()
    private var gridLayoutManager: GridLayoutManager? = null
    private var movieAdapter: MovieAdapter? = null
    private val KEY_RECYCLER_STATE = "recycler_state"
    private var bundleRclState: Bundle? = null
    private var searchText = ""

    /**
     * pagination variables
     */
    private var totalPage = 0
    private var currentPage = 1
    private var pageSize = 0
    private val visibleThreshold = 5
    private var loadMore = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        setObserver()

        binding.rootClDash.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.rootClDash.viewTreeObserver.removeOnGlobalLayoutListener(this)
                bindViews()
                setListeners()
                initScrollListener()
            }

        })
    }

    private fun setObserver() {
        dashboardViewModel.movie.observe(this) {
            it?.let { movieData ->
                if (movieData.page.pageNum.toInt() == 1) {
                    pageSize = movieData.page.pageSize.toInt()
                    totalPage = ceil(movieData.page.totalContentItems.toDouble() / pageSize).toInt()
                }
                setCurrentPage(movieData.page.contentItems.content)
            }
        }
    }

    private fun bindViews() {
        binding.txtTitle.isSelected = true

        gridLayoutManager = GridLayoutManager(this, getGridSpan(), LinearLayoutManager.VERTICAL, false)
        binding.rclMovie.layoutManager = gridLayoutManager
        dashboardViewModel.getMovieDataFromAsset(this, currentPage)

    }

    private fun setListeners() {
        binding.imgBack.setOnClickListener(this)
        binding.imgSearch.setOnClickListener(this)
        binding.closeOrClearSearch.setOnClickListener(this)

        binding.searchInputText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                // check search criteria on action button
            }
            false
        }

        binding.searchInputText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                // check search criteria on type
            }

        })

    }

    private fun setMovieAdapter(movieList: ArrayList<Content>) {
        movieAdapter = MovieAdapter(this, movieList)
        binding.rclMovie.adapter = movieAdapter
    }

    override fun onPause() {
        super.onPause()
        bundleRclState = Bundle()
        bundleRclState!!.putParcelable(KEY_RECYCLER_STATE, binding.rclMovie.layoutManager?.onSaveInstanceState())
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (bundleRclState != null) {
            Handler(Looper.getMainLooper()).postDelayed({
                binding.rclMovie.layoutManager?.onRestoreInstanceState(bundleRclState!!.parcelable(KEY_RECYCLER_STATE))
            }, 50)
        }

        // set spancount according orientation
        gridLayoutManager?.spanCount = getGridSpan()
        binding.rclMovie.layoutManager = gridLayoutManager
    }

    private fun setCurrentPage(currentPageList: List<Content>) {
        if (currentPageList.isNotEmpty()) {
            if (loadMore) {
                // handle pagination data in adapter
                loadMore = false
                val initialSize = (currentPage * pageSize) - 1
                movieAdapter?.updatePageItems(currentPageList)
                Log.e(TAG, "setCurrentPage: item range initialSize=$initialSize   updatedSize=${currentPageList.size}")
                movieAdapter?.notifyItemRangeInserted(initialSize, currentPageList.size)
                currentPage++

            } else {
                // set initial page data in adapter
                setMovieAdapter(ArrayList(currentPageList))
            }
        }
    }

    private fun initScrollListener() {
        // pagination calculation from scroll
        binding.rclMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                gridLayoutManager?.let {
                    if (!loadMore && currentPage < totalPage && it.itemCount - it.childCount <= it.findFirstVisibleItemPosition() + visibleThreshold) {
                        // load next page data form here
                        dashboardViewModel.getMovieDataFromAsset(this@DashboardActivity, currentPage + 1)
                        loadMore = true
                    }
                }
            }
        })
    }


    /* START - animated SearchView  */
    private fun openSearch() {
        binding.searchInputText.setText("")
        binding.flSearchView.isVisible = true
        val circularReveal = ViewAnimationUtils.createCircularReveal(
            binding.flSearchView,
            (binding.imgSearch.right + binding.imgSearch.left) / 2,
            (binding.imgSearch.top + binding.imgSearch.bottom) / 2,
            0f,
            binding.toolbar.width.toFloat()
        )
        circularReveal.duration = 300
        circularReveal.start()
    }

    private fun hideSearchLayout(): Boolean {
        if (binding.flSearchView.isVisible) {
            val circularConceal = ViewAnimationUtils.createCircularReveal(
                binding.flSearchView,
                (binding.imgSearch.right + binding.imgSearch.left) / 2,
                (binding.imgSearch.top + binding.imgSearch.bottom) / 2,
                binding.toolbar.width.toFloat(),
                0f
            )

            circularConceal.duration = 200
            circularConceal.start()
            circularConceal.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator) = Unit
                override fun onAnimationCancel(animation: Animator) = Unit
                override fun onAnimationStart(animation: Animator) = Unit
                override fun onAnimationEnd(animation: Animator) {
                    binding.flSearchView.isVisible = false
                    binding.searchInputText.setText("")
                    binding.searchInputText.text.clear()
                    searchText = ""
                    circularConceal.removeAllListeners()
                }
            })

        }
        return true
    }
    /* END - animated SearchView  */

    override fun onClick(v: View?) {
        when (v) {
            binding.imgSearch -> openSearch()
            binding.closeOrClearSearch -> {
                if (binding.searchInputText.text.isEmpty()) {
                    hideSearchLayout()
                } else {
                    binding.searchInputText.setText("")
                    binding.searchInputText.text.clear()
                    searchText = ""
                }
            }

            binding.imgBack -> {
                this@DashboardActivity.finishAffinity()
                exitProcess(0)
            }
        }
    }


}