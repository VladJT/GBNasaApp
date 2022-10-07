package jt.projects.gbnasaapp.viewmodel.mars

import jt.projects.gbnasaapp.model.mars.MarsServerResponseData

sealed class MarsData {
    data class Success(val serverResponseData: MarsServerResponseData) :
        MarsData()

    data class Error(val error: Throwable) : MarsData()
    data class Loading(val progress: Int?) : MarsData()
}