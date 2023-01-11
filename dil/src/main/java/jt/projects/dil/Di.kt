package jt.projects.dil

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
}