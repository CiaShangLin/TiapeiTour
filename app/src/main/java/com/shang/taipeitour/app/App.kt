package com.shang.taipeitour.app

import androidx.multidex.MultiDexApplication
import com.shang.taipeitour.di.mainModule
import com.shang.taipeitour.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                mainModule,
                networkModule
            )
        }
    }
}