package jt.projects.gbnasaapp.viewmodel.notes

import jt.projects.gbnasaapp.model.mars.MarsServerResponseData
import jt.projects.gbnasaapp.model.notes.NotesData

sealed class NotesDataStatus {
    data class Success(val responce: ArrayList<NotesData>?) :
        NotesDataStatus()

    data class Error(val error: Throwable) : NotesDataStatus()
    data class Loading(val progress: Int?) : NotesDataStatus()
}