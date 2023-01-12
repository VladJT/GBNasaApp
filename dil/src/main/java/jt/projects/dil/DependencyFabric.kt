package jt.projects.dil

import android.app.Application

abstract class DependencyFabric<T : Any>(protected val creator: () -> T) {
    abstract fun get(): Any
}


class Singleton<T : Any>(creator: () -> T) : DependencyFabric<T>(creator) {
    private val dependency: Any by lazy { creator.invoke() }

    override fun get(): Any = dependency
}


class Fabric<T : Any>(creator: () -> T) : DependencyFabric<T>(creator) {
    override fun get(): Any = creator()
}


class DiModule(private val block: DiModule.() -> Unit) {
    lateinit var app: Application

    fun install(app: Application) {
        block()
        this.app = app
    }

    inline fun <reified T : Any> singleton(noinline creator: () -> T) =
        Di.add(Singleton<T>(creator))

    inline fun <reified T : Any> fabric(noinline creator: () -> T) =
        Di.add(Fabric<T>(creator))
}