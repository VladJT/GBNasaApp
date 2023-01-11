package jt.projects.gbnasaapp.di

import jt.projects.gbnasaapp.BuildConfig
import jt.projects.gbnasaapp.model.SharedPref
import jt.projects.gbnasaapp.model.mars.IMarsRepo
import jt.projects.gbnasaapp.model.mars.MarsRetrofitImpl
import jt.projects.gbnasaapp.model.pod.IPodRepo
import jt.projects.gbnasaapp.model.pod.PODRetrofitImpl
import kotlin.reflect.KClass

interface Di {
    fun <T : Any> get(clazz: KClass<T>): T
    fun <T : Any> addDependency(clazz: KClass<T>, dependency: T)
    fun <T : Any> addDependency(dependency: T)
}

class DiImpl : Di {
    private val dependenciesHolder = HashMap<KClass<*>, Any>()

    override fun <T : Any> addDependency(clazz: KClass<T>, dependency: T) {
        dependenciesHolder[clazz] = dependency
    }

    override fun <T : Any> addDependency(dependency: T) {
        dependenciesHolder[dependency::class] = dependency
    }

    override fun <T : Any> get(clazz: KClass<T>): T {
        if (dependenciesHolder.containsKey(clazz)) {
            return dependenciesHolder[clazz] as T
        } else {
            throw IllegalArgumentException("Not found class")
        }
    }

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
        addDependency(IPodRepo::class, podRepo)
        addDependency(IMarsRepo::class, marsRepo)
        addDependency(sharedPref)
    }
}