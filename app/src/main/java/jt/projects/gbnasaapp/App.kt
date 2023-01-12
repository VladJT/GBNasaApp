package jt.projects.gbnasaapp

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import jt.projects.gbnasaapp.di.appModule


class App : Application() {
    init {
        appModule.install(this)
    }
}

val Context.app: App get() = applicationContext as App
val Fragment.app: App get() = requireContext().applicationContext as App