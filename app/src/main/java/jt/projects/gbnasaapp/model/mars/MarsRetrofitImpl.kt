package jt.projects.gbnasaapp.model.mars

import jt.projects.gbnasaapp.model.retrofit.RetrofitCallback
import jt.projects.gbnasaapp.model.retrofit.RetrofitImpl
import java.time.LocalDate

class MarsRetrofitImpl : RetrofitImpl() {
    private val retrofitImpl = getRetrofitImpl<MarsAPI>()

    fun getMarsPhotosByDate(callback: RetrofitCallback<MarsServerResponseData>, date: LocalDate) {
        if (isApiKeyGood(callback)) {
            retrofitImpl.getMarsPhotosByDate(apiKey, date.toString())
                .enqueue(getCallbackFromRetrofit(callback))
        }
    }
}