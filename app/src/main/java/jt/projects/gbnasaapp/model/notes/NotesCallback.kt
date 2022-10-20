package jt.projects.gbnasaapp.model.notes

import java.io.IOException

interface NotesCallback {
    fun onResponse(data: ArrayList<NotesData>?)
    fun onFailure(e: IOException)
}