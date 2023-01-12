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