package jt.projects.gbnasaapp.viewmodel.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jt.projects.gbnasaapp.model.notes.INotesRepository
import jt.projects.gbnasaapp.model.notes.NotesCallback
import jt.projects.gbnasaapp.model.notes.NotesData
import jt.projects.gbnasaapp.model.notes.NotesLocalRepository
import java.io.IOException

class NotesViewModel(
    private val liveDataForViewToObserve: MutableLiveData<NotesDataStatus> =
        MutableLiveData(),
    private val reposiroty: INotesRepository = NotesLocalRepository()
) : ViewModel() {

    fun getLiveData(): LiveData<NotesDataStatus> {
        reposiroty.getData(callback)
        return liveDataForViewToObserve
    }


    private val callback = object : NotesCallback {
        override fun onResponse(data: ArrayList<NotesData>?) {
            liveDataForViewToObserve.value = NotesDataStatus.Success(data)
        }

        override fun onFailure(e: IOException) {
            liveDataForViewToObserve.value = NotesDataStatus.Error(e)
        }
    }

}