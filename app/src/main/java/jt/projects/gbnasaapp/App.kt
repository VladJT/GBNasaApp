package jt.projects.gbnasaapp

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import jt.projects.gbnasaapp.di.Di
import jt.projects.gbnasaapp.di.DiImpl


class App : Application() {
    companion object {
        lateinit var instance: App
    }

    private val isMainProcess = true
    lateinit var di: Di

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (isMainProcess) {
            di = DiImpl()
        }
    }
}

val Context.app: App get() = applicationContext as App
val Fragment.app: App get() = requireContext().applicationContext as App