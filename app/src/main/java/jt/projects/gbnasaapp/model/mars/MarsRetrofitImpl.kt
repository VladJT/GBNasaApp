package jt.projects.gbnasaapp.model.mars

import jt.projects.gbnasaapp.BuildConfig
import jt.projects.gbnasaapp.model.retrofit.RetrofitCallback
import jt.projects.gbnasaapp.model.retrofit.RetrofitImpl
import java.time.LocalDate

class MarsRetrofitImpl : RetrofitImpl() {
    private val retrofitImpl = getRetrofitImpl<MarsAPI>()

    fun getMarsPhotosByDate(callback: RetrofitCallback<MarsServerResponseData>, date: LocalDate) {
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            callback.onFailure(Throwable("You need API key"))
        } else {
            retrofitImpl.getMarsPhotosByDate(apiKey, date.toString())
                .enqueue(getCallbackFromRetrofit(callback))
        }
    }
}