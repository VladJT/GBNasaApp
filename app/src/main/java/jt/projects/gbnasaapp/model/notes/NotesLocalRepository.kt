package jt.projects.gbnasaapp.model.notes

import jt.projects.gbnasaapp.model.notes.INotesRepository
import jt.projects.gbnasaapp.model.notes.NotesData
import jt.projects.gbnasaapp.viewmodel.notes.NotesDataStatus

class NotesLocalRepository : INotesRepository {

    override fun getData(callback: NotesCallback) {
        val data = arrayListOf(
            NotesData(type = 0),
            NotesData(topic = "Тема 1", fullText = "Содержание 1"),
            NotesData(topic = "Тема 2", fullText = "Содержание 2"))
        callback.onResponse(data)
    }
}