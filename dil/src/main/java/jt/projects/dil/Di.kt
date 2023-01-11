package jt.projects.dil

import kotlin.reflect.KClass

interface Di {
    fun <T : Any> get(clazz: KClass<T>): T
    fun <T : Any> add(clazz: KClass<T>, dependencyFabric: DependencyFabric)
    fun add(dependencyFabric: DependencyFabric)
}


object DiImpl : Di {
    private val dependenciesHolder = HashMap<KClass<*>, DependencyFabric>()

    override fun <T : Any> add(clazz: KClass<T>, dependencyFabric: DependencyFabric) {
        dependenciesHolder[clazz] = dependencyFabric
    }

    // TODO not worked
    override fun add(dependencyFabric: DependencyFabric) {
        dependenciesHolder[dependencyFabric::class] = dependencyFabric
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
    return DiImpl.get(T::class)
}

inline fun <reified T : Any> inject() = lazy { get<T>() }

abstract class DependencyFabric(protected val creator: () -> Any) {
    abstract fun get(): Any
}

class Singleton(creator: () -> Any) : DependencyFabric(creator) {
    private val dependency: Any by lazy { creator.invoke() }

    override fun get(): Any = dependency
}

class Fabric(creator: () -> Any) : DependencyFabric(creator) {
    override fun get(): Any = creator()
}