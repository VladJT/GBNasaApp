package jt.projects.dil

import kotlin.reflect.KClass

data class Qualifier(
    private val clazz: KClass<*>,
    private val name: String = "default_name"
)

interface IDiHolder {
    fun <T : Any> add(qualifier: Qualifier, dependencyFabric: DependencyFabric<T>)
    fun <T : Any> get(qualifier: Qualifier): T
}

object Di : IDiHolder {
    private val dependenciesHolder = HashMap<Qualifier, DependencyFabric<*>>()

    override fun <T : Any> add(qualifier: Qualifier, dependencyFabric: DependencyFabric<T>) {
        if (dependenciesHolder.containsKey(qualifier)) {
            throw java.lang.IllegalStateException("Dep is in Graph Ready")
        } else {
            dependenciesHolder[qualifier] = dependencyFabric
        }
    }

    inline fun <reified T : Any> add(dependencyFabric: DependencyFabric<T>) {
        add(Qualifier(clazz = T::class), dependencyFabric)
    }

    override fun <T : Any> get(qualifier: Qualifier): T {
        val dependencyFabric = dependenciesHolder[qualifier]
        if (dependencyFabric != null) {
            return dependencyFabric.get() as T
        } else {
            throw IllegalArgumentException("Not found class")
        }
    }

}


inline fun <reified T : Any> get(): T = Di.get(Qualifier(clazz = T::class))

inline fun <reified T : Any> get(name: String): T = Di.get(Qualifier(clazz = T::class, name = name))

inline fun <reified T : Any> inject() = lazy { get<T>() }

inline fun <reified T : Any> inject(name: String) = lazy { get<T>(name) }