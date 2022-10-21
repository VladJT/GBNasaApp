package jt.projects.gbnasaapp.model.notes

import jt.projects.gbnasaapp.model.notes.NotesData
import java.io.IOException


interface INotesRepository {
    fun getData(callback: NotesCallback)
}