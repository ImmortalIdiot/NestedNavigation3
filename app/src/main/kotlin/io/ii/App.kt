package io.ii

import android.app.Application
import io.ii.di.projectsDiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)

            modules(projectsDiModule)
        }
    }
}
