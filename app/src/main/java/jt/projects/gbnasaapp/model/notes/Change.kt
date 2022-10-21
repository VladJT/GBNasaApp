package jt.projects.gbnasaapp.model.notes

//вспомогательный data-класс Change, который будет хранить в себе два массива: старый список
//элементов и новый
data class Change<out T>(
    val oldData: T,
    val newData: T
)


fun <T> createCombinedPayload(payloads: List<Change<T>>): Change<T> {
    assert(payloads.isNotEmpty())
    val firstChange = payloads.first()
    val lastChange = payloads.last()
    return Change(firstChange.oldData, lastChange.newData)
}
