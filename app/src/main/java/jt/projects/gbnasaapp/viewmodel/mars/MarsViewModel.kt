package jt.projects.gbnasaapp.viewmodel.mars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jt.projects.gbnasaapp.model.mars.MarsRetrofitImpl
import jt.projects.gbnasaapp.model.mars.MarsServerResponseData
import jt.projects.gbnasaapp.model.retrofit.RetrofitCallback
import java.time.LocalDate

class MarsViewModel(
    private val liveDataForViewToObserve: MutableLiveData<MarsData> =
        MutableLiveData(),
    private val retrofitImpl: MarsRetrofitImpl = MarsRetrofitImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<MarsData> {
        return liveDataForViewToObserve
    }

    fun loadMarsByDate(date: LocalDate) {
        retrofitImpl.getMarsPhotosByDate(callback, date)
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