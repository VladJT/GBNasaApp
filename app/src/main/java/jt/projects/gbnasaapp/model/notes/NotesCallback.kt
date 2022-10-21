package jt.projects.gbnasaapp.model.notes

import java.io.IOException


interface NotesCallback {
    fun onResponse(data: MutableList<Pair<NotesData, Boolean>>?)
    fun onFailure(e: IOException)
}