package jt.projects.gbnasaapp.model.pod

import jt.projects.gbnasaapp.model.retrofit.RetrofitCallback
import java.time.LocalDate

interface IPodRepo {
    fun getPictureOfTheDay(callback: RetrofitCallback<PODServerResponseData>) {
    }

    fun getPictureOfTheDayByDate(
        callback: RetrofitCallback<PODServerResponseData>,
        date: LocalDate
    )
}