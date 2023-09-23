package com.showcase.moviewave.modules

import com.showcase.moviewave.viewmodels.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Harshali.Sachani on 9/22/2023.
 */


/**
 * app koin module define here
 */
val koinModules = module {

    viewModel {
        DashboardViewModel()
    }

}
