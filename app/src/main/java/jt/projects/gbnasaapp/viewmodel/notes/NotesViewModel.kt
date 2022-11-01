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
    private var isNewList = false

    fun getLiveData(): LiveData<NotesDataStatus> {
        reposiroty.getData(callback)
        return liveDataForViewToObserve
    }


    private val callback = object : NotesCallback {
        override fun onResponse(data: MutableList<Pair<NotesData, Boolean>>?) {
            liveDataForViewToObserve.value = NotesDataStatus.Success(data)
        }

        override fun onFailure(e: IOException) {
            liveDataForViewToObserve.value = NotesDataStatus.Error(e)
        }
    }

    fun setTestData() {
        val d: MutableList<Pair<NotesData, Boolean>>? =
            createTestItems(isNewList)?.map { it } as MutableList<Pair<NotesData, Boolean>>?
        liveDataForViewToObserve.value = NotesDataStatus.Success(d)
        isNewList = !isNewList
    }

    private fun createTestItems(instanceNumber: Boolean): MutableList<Pair<NotesData, Boolean>>? {
        return when (instanceNumber) {
            false -> mutableListOf(
                Pair(NotesData(id = -1, type = NotesData.TYPE_HEADER), false),
                Pair(NotesData(id = 0, topic = "11", fullText = "Купить цветы жене"), false),
                Pair(NotesData(id = 1, topic = "232", fullText = "Купить цветы жене"), false),
                Pair(
                    NotesData(id = 2, topic = "22", fullText = "Поменять резину на зимнюю"),
                    false
                ),
                Pair(
                    NotesData(id = 3, topic = "апрар", fullText = "Поменять резину на зимнюю"),
                    false
                )
            )
            true -> mutableListOf(
                Pair(NotesData(id = -1, type = NotesData.TYPE_HEADER), false),
                Pair(NotesData(id = 0, topic = "11", fullText = "Купить цветы жене"), false),
                Pair(NotesData(id = 1, topic = "45", fullText = "45 цветы жене"), false),
                Pair(
                    NotesData(id = 2, topic = "22", fullText = "Поменять резину на зимнюю"),
                    false
                ),
                Pair(NotesData(id = 3, topic = "!!", fullText = "Поменять резину на зимнюю"), false)
            )
        }
    }
}