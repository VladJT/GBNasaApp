package jt.projects.gbnasaapp.model.notes

class NotesLocalRepository : INotesRepository {

    override fun getData(callback: NotesCallback) {
        val data = arrayListOf(
            Pair(NotesData(type = NotesData.TYPE_HEADER), false),
            Pair(NotesData(topic = "17.11 Цветы", fullText = "Купить цветы жене"), false),
            Pair(NotesData(topic = "19.11 Авто", fullText = "Поменять резину на зимнюю"), false)
        )
        callback.onResponse(data)
    }
}
