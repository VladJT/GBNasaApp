package jt.projects.dil

import kotlin.reflect.KClass

interface IDiHolder {
    fun <T : Any> get(clazz: KClass<T>): T
    fun <T : Any> add(clazz: KClass<T>, dependencyFabric: DependencyFabric<T>)
}

object Di : IDiHolder {
    private val dependenciesHolder = HashMap<KClass<*>, DependencyFabric<*>>()

    override fun <T : Any> add(clazz: KClass<T>, dependencyFabric: DependencyFabric<T>) {
        dependenciesHolder[clazz] = dependencyFabric
    }

    inline fun <reified T : Any> add(dependencyFabric: DependencyFabric<T>) {
        add(T::class, dependencyFabric)
    }

    override fun <T : Any> get(clazz: KClass<T>): T {
        val dependencyFabric = dependenciesHolder[clazz]
        if (dependencyFabric != null) {
            return dependencyFabric.get() as T
        } else {
            throw IllegalArgumentException("Not found class")
        }
    }
}

inline fun <reified T : Any> get(): T {
    return Di.get(T::class)
}

inline fun <reified T : Any> inject() = lazy { get<T>() }