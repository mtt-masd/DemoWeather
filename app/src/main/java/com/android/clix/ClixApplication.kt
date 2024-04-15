package com.android.clix

import android.app.Application
import com.android.clix.koin.components.applicationComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.unloadKoinModules

class ClixApplication : Application() {
    init {
        instance = this
    }

    companion object {
        lateinit var instance: ClixApplication

        fun resetKoin() {
            unloadKoinModules(applicationComponent)
            loadKoinModules(applicationComponent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(instance)
            modules(applicationComponent)
        }
    }
}