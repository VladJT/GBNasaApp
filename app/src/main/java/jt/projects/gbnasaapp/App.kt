package jt.projects.gbnasaapp

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import jt.projects.gbnasaapp.model.SharedPref
import jt.projects.gbnasaapp.model.mars.IMarsRepo
import jt.projects.gbnasaapp.model.mars.MarsRetrofitImpl
import jt.projects.gbnasaapp.model.pod.IPodRepo
import jt.projects.gbnasaapp.model.pod.PODRetrofitImpl


class App : Application() {
    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    /**
     * REST API
     */
    val apiKey by lazy { BuildConfig.NASA_API_KEY }
    val baseURL = "https://api.nasa.gov/"

    val podRepo: IPodRepo by lazy { PODRetrofitImpl() }
    val marsRepo: IMarsRepo by lazy { MarsRetrofitImpl() }



    /**
     * SHARED PREFS
     */
    private val spDbName = "settings"
    private val spDbKey = "ALL_SETTINGS_IN_JSON_FORMAT"
    val sharedPref: SharedPref by lazy {
        SharedPref(spDbName, spDbKey)
    }
}

val Context.app: App get() = applicationContext as App
val Fragment.app: App get() = requireContext() as App