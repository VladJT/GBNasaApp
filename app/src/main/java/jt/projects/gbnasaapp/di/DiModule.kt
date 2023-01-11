package jt.projects.gbnasaapp.di

import android.content.Context
import jt.projects.dil.DiImpl
import jt.projects.dil.Fabric
import jt.projects.dil.Singleton
import jt.projects.gbnasaapp.App
import jt.projects.gbnasaapp.BuildConfig
import jt.projects.gbnasaapp.model.SharedPref
import jt.projects.gbnasaapp.model.mars.IMarsRepo
import jt.projects.gbnasaapp.model.mars.MarsRetrofitImpl
import jt.projects.gbnasaapp.model.pod.IPodRepo
import jt.projects.gbnasaapp.model.pod.PODRetrofitImpl

class DiModule(app: App) {
    /**
     * REST API
     */
    private val apiKey by lazy { BuildConfig.NASA_API_KEY }
    private val baseURL = "https://api.nasa.gov/"

    private val podRepo: IPodRepo by lazy { PODRetrofitImpl(apiKey, baseURL) }
    private val marsRepo: IMarsRepo by lazy { MarsRetrofitImpl(apiKey, baseURL) }

    /**
     * SHARED PREFS
     */
    private val spDbName = "settings"
    private val spDbKey = "ALL_SETTINGS_IN_JSON_FORMAT"
    private val sharedPref: SharedPref by lazy {
        SharedPref(
            app.applicationContext.getSharedPreferences(
                spDbName,
                Context.MODE_PRIVATE
            ), spDbKey
        )
    }

    init {
        DiImpl.add(IPodRepo::class, Singleton { podRepo })
        DiImpl.add(IMarsRepo::class, Fabric { marsRepo })
        DiImpl.add(SharedPref::class, Singleton { sharedPref })
    }
}