package com.showcase.movieapp.utils

import android.app.Application
import com.showcase.movieapp.modules.koinModules
import org.koin.core.context.startKoin

/**
 * Created by Harshali.Sachani on 9/22/2023.
 */
class MovieApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // initialize koin modules
        startKoin {
            modules(koinModules)
        }

    }

}