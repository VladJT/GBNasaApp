package jt.projects.gbnasaapp.model.mars

import jt.projects.gbnasaapp.model.retrofit.RetrofitCallback
import jt.projects.gbnasaapp.model.retrofit.RetrofitImpl
import java.time.LocalDate

class MarsRetrofitImpl(apiKey: String, baseUrl: String) : RetrofitImpl(apiKey, baseUrl), IMarsRepo {
    private val retrofitImpl = getRetrofitImpl<MarsAPI>()

    override fun getMarsPhotosByDate(
        callback: RetrofitCallback<MarsServerResponseData>,
        date: LocalDate
    ) {
        if (isApiKeyGood(callback)) {
            retrofitImpl.getMarsPhotosByDate(apiKey, date.toString())
                .enqueue(getCallbackFromRetrofit(callback))
        }
    }
}