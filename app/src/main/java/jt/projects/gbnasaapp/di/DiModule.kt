package jt.projects.gbnasaapp.di

import jt.projects.dil.Di
import jt.projects.gbnasaapp.BuildConfig
import jt.projects.gbnasaapp.model.SharedPref
import jt.projects.gbnasaapp.model.mars.IMarsRepo
import jt.projects.gbnasaapp.model.mars.MarsRetrofitImpl
import jt.projects.gbnasaapp.model.pod.IPodRepo
import jt.projects.gbnasaapp.model.pod.PODRetrofitImpl

class DiModule(di: Di) {
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
        SharedPref(spDbName, spDbKey)
    }


    init {
        di.addDependency(IPodRepo::class, podRepo)
        di.addDependency(IMarsRepo::class, marsRepo)
        di.addDependency(sharedPref)
    }
}