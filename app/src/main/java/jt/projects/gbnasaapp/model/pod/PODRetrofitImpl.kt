package jt.projects.gbnasaapp.model.pod

import jt.projects.gbnasaapp.model.retrofit.RetrofitCallback
import jt.projects.gbnasaapp.model.retrofit.RetrofitImpl
import java.time.LocalDate

class PODRetrofitImpl : RetrofitImpl(), IPodRepo {
    private val retrofitImpl = getRetrofitImpl<PictureOfTheDayAPI>()

    override fun getPictureOfTheDay(callback: RetrofitCallback<PODServerResponseData>) {
        if (isApiKeyGood(callback)) {
            retrofitImpl.getPictureOfTheDay(apiKey).enqueue(getCallbackFromRetrofit(callback))
        }
    }

    override fun getPictureOfTheDayByDate(
        callback: RetrofitCallback<PODServerResponseData>,
        date: LocalDate
    ) {
        if (isApiKeyGood(callback)) {
            retrofitImpl.getPictureOfTheDayByDate(apiKey, date.toString())
                .enqueue(getCallbackFromRetrofit(callback))
        }
    }
}
