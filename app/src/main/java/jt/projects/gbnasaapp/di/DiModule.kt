package jt.projects.gbnasaapp.di

import android.content.Context
import jt.projects.dil.DiModule
import jt.projects.gbnasaapp.BuildConfig
import jt.projects.gbnasaapp.model.SharedPref
import jt.projects.gbnasaapp.model.mars.IMarsRepo
import jt.projects.gbnasaapp.model.mars.MarsRetrofitImpl
import jt.projects.gbnasaapp.model.pod.IPodRepo
import jt.projects.gbnasaapp.model.pod.PODRetrofitImpl


val appModule = DiModule {
    /**
     * REST API
     */
    val apiKey by lazy { BuildConfig.NASA_API_KEY }
    val baseURL = "https://api.nasa.gov/"

    singleton<IPodRepo>("mainPodRepo") { PODRetrofitImpl(apiKey, baseURL) }
    singleton<IPodRepo>("secondPodRepo") { PODRetrofitImpl(apiKey, baseURL) }
    fabric<IMarsRepo> { MarsRetrofitImpl(apiKey, baseURL) }

    /**
     * SHARED PREFS
     */
    val spDbName = "settings"
    val spDbKey = "ALL_SETTINGS_IN_JSON_FORMAT"

    singleton {
        SharedPref(
            app.applicationContext.getSharedPreferences(
                spDbName,
                Context.MODE_PRIVATE
            ), spDbKey
        )
    }
}