package jt.projects.gbnasaapp.model.mars

import jt.projects.gbnasaapp.model.retrofit.RetrofitCallback
import java.time.LocalDate

interface IMarsRepo {
    fun getMarsPhotosByDate(callback: RetrofitCallback<MarsServerResponseData>, date: LocalDate) {}
}