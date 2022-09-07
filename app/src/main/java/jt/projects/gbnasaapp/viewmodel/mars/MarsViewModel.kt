package jt.projects.gbnasaapp.viewmodel.mars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jt.projects.gbnasaapp.BuildConfig
import jt.projects.gbnasaapp.model.mars.MarsRetrofitImpl
import jt.projects.gbnasaapp.model.mars.MarsServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class MarsViewModel(
    private val liveDataForViewToObserve: MutableLiveData<MarsData> =
        MutableLiveData(),
    private val retrofitImpl: MarsRetrofitImpl = MarsRetrofitImpl()
) : ViewModel() {

    fun getDate(): LiveData<MarsData> {
        return liveDataForViewToObserve
    }

    fun loadMarsByDate(date: LocalDate) {
        sendServerRequest(date)
    }

    private val callback = object : Callback<MarsServerResponseData> {
        override fun onResponse(
            call: Call<MarsServerResponseData>,
            response: Response<MarsServerResponseData>
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataForViewToObserve.value =
                    MarsData.Success(response.body()!!)
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataForViewToObserve.value =
                        MarsData.Error(Throwable("Unidentified error"))
                } else {
                    liveDataForViewToObserve.value =
                        MarsData.Error(Throwable(message))
                }
            }
        }

        override fun onFailure(call: Call<MarsServerResponseData>, t: Throwable) {
            liveDataForViewToObserve.value = MarsData.Error(t)
        }
    }

    private fun sendServerRequest(date: LocalDate?) {
        liveDataForViewToObserve.value = MarsData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataForViewToObserve.value =
                MarsData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getMarsPhotosByDate(apiKey, date.toString()).enqueue(callback)
        }
    }

}