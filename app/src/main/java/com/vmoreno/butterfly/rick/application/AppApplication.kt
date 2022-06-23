package com.vmoreno.butterfly.rick.application

import android.app.Application
import com.vmoreno.butterfly.rick.datai.di.AppComponent.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AppApplication : Application() {

    private fun provideDependency() = appComponent

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@AppApplication)
            modules(provideDependency())
        }
    }
}