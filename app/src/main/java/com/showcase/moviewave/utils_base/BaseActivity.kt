package com.showcase.moviewave.utils_base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Harshali.Sachani on 9/23/2023.
 */
open class BaseActivity : AppCompatActivity() {

    val TAG = javaClass.simpleName

    fun hideKeyboard(view: View) {
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showKeyboard(view: View) {
        view.requestFocus()
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

}