package com.showcase.movieapp.modules

import com.showcase.movieapp.viewmodels.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Harshali.Sachani on 9/22/2023.
 */


/**
 * app kon module define here
 */
val koinModules = module {

    viewModel {
        DashboardViewModel()
    }

}
