package jt.projects.gbnasaapp.model.retrofit


interface RetrofitCallback<T> {
    fun onResponse(data: T)
    fun onFailure(e: Throwable)
}