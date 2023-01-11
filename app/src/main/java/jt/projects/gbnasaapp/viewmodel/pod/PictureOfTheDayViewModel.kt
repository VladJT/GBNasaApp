package jt.projects.gbnasaapp.viewmodel.pod

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jt.projects.dil.inject
import jt.projects.gbnasaapp.model.pod.IPodRepo
import jt.projects.gbnasaapp.model.pod.PODServerResponseData
import jt.projects.gbnasaapp.model.retrofit.RetrofitCallback
import java.time.LocalDate

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayData> =
        MutableLiveData()
) : ViewModel() {

    private val podRepo: IPodRepo by inject()

    fun getLiveData(): LiveData<PictureOfTheDayData> {
        return liveDataForViewToObserve
    }

    fun loadPictureOfTheDay() {
        podRepo.getPictureOfTheDay(callback)
    }


    fun loadPictureOfTheDayByDate(date: LocalDate) {
        podRepo.getPictureOfTheDayByDate(callback, date)
    }

    private val callback = object : RetrofitCallback<PODServerResponseData> {
        override fun onResponse(data: PODServerResponseData) {
            liveDataForViewToObserve.value = PictureOfTheDayData.Success(data)
        }

        override fun onFailure(e: Throwable) {
            liveDataForViewToObserve.value = PictureOfTheDayData.Error(e)
        }
    }
}