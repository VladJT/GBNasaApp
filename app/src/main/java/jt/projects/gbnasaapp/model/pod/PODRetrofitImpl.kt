package jt.projects.gbnasaapp.model.pod

import jt.projects.gbnasaapp.model.retrofit.RetrofitCallback
import jt.projects.gbnasaapp.model.retrofit.RetrofitImpl
import java.time.LocalDate

class PODRetrofitImpl : RetrofitImpl() {
    private val retrofitImpl = getRetrofitImpl<PictureOfTheDayAPI>()

    fun getPictureOfTheDay(callback: RetrofitCallback<PODServerResponseData>) {
        if(isApiKeyGood(callback)) {
            retrofitImpl.getPictureOfTheDay(apiKey).enqueue(getCallbackFromRetrofit(callback))
        }
    }

    fun getPictureOfTheDayByDate(
        callback: RetrofitCallback<PODServerResponseData>,
        date: LocalDate
    ) {
        if(isApiKeyGood(callback)) {
            retrofitImpl.getPictureOfTheDayByDate(apiKey, date.toString())
                .enqueue(getCallbackFromRetrofit(callback))
        }
    }
}
