package app.learn.kotlin

import android.app.Application
import app.learn.kotlin.koin.appComponent
import org.koin.android.ext.android.startKoin

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, appComponent)
    }
}