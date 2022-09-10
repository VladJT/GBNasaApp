package jt.projects.gbnasaapp.model.pod

import jt.projects.gbnasaapp.BuildConfig
import jt.projects.gbnasaapp.model.retrofit.RetrofitCallback
import jt.projects.gbnasaapp.model.retrofit.RetrofitImpl
import java.time.LocalDate

class PODRetrofitImpl : RetrofitImpl() {
    private val retrofitImpl = getRetrofitImpl<PictureOfTheDayAPI>()

    fun getPictureOfTheDay(callback: RetrofitCallback<PODServerResponseData>) {
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            callback.onFailure(Throwable("You need API key"))
        } else {
            retrofitImpl.getPictureOfTheDay(apiKey)
                .enqueue(getCallbackFromRetrofit(callback))
        }
    }

    fun getPictureOfTheDayByDate(
        callback: RetrofitCallback<PODServerResponseData>,
        date: LocalDate
    ) {
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            callback.onFailure(Throwable("You need API key"))
        } else {
            retrofitImpl.getPictureOfTheDayByDate(apiKey, date.toString())
                .enqueue(getCallbackFromRetrofit(callback))
        }
    }
}
