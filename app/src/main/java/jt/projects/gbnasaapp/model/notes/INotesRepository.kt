package jt.projects.gbnasaapp.model.notes

interface INotesRepository {
    fun getData(callback: NotesCallback)
}