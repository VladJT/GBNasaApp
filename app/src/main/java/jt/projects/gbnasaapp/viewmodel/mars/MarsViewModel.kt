package jt.projects.gbnasaapp.viewmodel.mars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jt.projects.dil.inject
import jt.projects.gbnasaapp.model.mars.IMarsRepo
import jt.projects.gbnasaapp.model.mars.MarsServerResponseData
import jt.projects.gbnasaapp.model.retrofit.RetrofitCallback
import java.time.LocalDate

class MarsViewModel(
    private val liveDataForViewToObserve: MutableLiveData<MarsData> =
        MutableLiveData()
) : ViewModel() {

    private val marsRepo: IMarsRepo by inject()

    fun getLiveData(): LiveData<MarsData> {
        return liveDataForViewToObserve
    }

    fun loadMarsByDate(date: LocalDate) {
        marsRepo.getMarsPhotosByDate(callback, date)
    }

    private val callback = object : RetrofitCallback<MarsServerResponseData> {
        override fun onResponse(data: MarsServerResponseData) {
            liveDataForViewToObserve.value = MarsData.Success(data)
        }

        override fun onFailure(e: Throwable) {
            liveDataForViewToObserve.value = MarsData.Error(e)
        }
    }

}