package jt.projects.gbnasaapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jt.projects.gbnasaapp.model.pod.PODRetrofitImpl
import jt.projects.gbnasaapp.BuildConfig
import jt.projects.gbnasaapp.model.dto.PODServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayData> =
        MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) : ViewModel() {

    fun getDate(): LiveData<PictureOfTheDayData> {
        return liveDataForViewToObserve
    }

    fun loadPictureOfTheDay() {
        sendServerRequest(null)
    }

    fun loadPictureOfTheDayByDate(date: LocalDate) {
        sendServerRequest(date)
    }

    private val callback = object : Callback<PODServerResponseData> {
        override fun onResponse(
            call: Call<PODServerResponseData>,
            response: Response<PODServerResponseData>
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataForViewToObserve.value =
                    PictureOfTheDayData.Success(response.body()!!)
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataForViewToObserve.value =
                        PictureOfTheDayData.Error(Throwable("Unidentified error"))
                } else {
                    liveDataForViewToObserve.value =
                        PictureOfTheDayData.Error(Throwable(message))
                }
            }
        }

        override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
            liveDataForViewToObserve.value = PictureOfTheDayData.Error(t)
        }
    }

    private fun sendServerRequest(date: LocalDate?) {
        liveDataForViewToObserve.value = PictureOfTheDayData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataForViewToObserve.value =
                PictureOfTheDayData.Error(Throwable("You need API key"))
        } else {
            if (date == null) {
                retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(callback)
            } else
                retrofitImpl.getRetrofitImpl().getPictureOfTheDayByDate(apiKey, date.toString())
                    .enqueue(callback)
        }
    }
}