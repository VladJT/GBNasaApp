package jt.projects.gbnasaapp.di

import android.content.Context

import jt.projects.gbnasaapp.model.SharedPref
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val appModule = module {
    single<SharedPref> { SharedPref(androidContext()) }


}
