package jt.projects.gbnasaapp.viewmodel.pod

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jt.projects.gbnasaapp.model.pod.PODRetrofitImpl
import jt.projects.gbnasaapp.model.pod.PODServerResponseData
import jt.projects.gbnasaapp.model.retrofit.RetrofitCallback
import java.time.LocalDate

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayData> =
        MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<PictureOfTheDayData> {
        return liveDataForViewToObserve
    }

    fun loadPictureOfTheDay() {
        retrofitImpl.getPictureOfTheDay(callback)
    }


    fun loadPictureOfTheDayByDate(date: LocalDate) {
        retrofitImpl.getPictureOfTheDayByDate(callback, date)
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