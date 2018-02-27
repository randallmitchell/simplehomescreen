package com.methodsignature.simplehomescreen

import android.content.Context
import com.methodsignature.simplehomescreen.interactors.InstallInitialDataInteractor

class Application: android.app.Application() {

    companion object {
        fun from(context: Context): Application {
            return context.applicationContext as Application
        }

        fun scopeFrom(context:Context): ApplicationScope {
            return from(context).applicationScope
        }
    }

    private val applicationScope: ApplicationScope by lazy {
        ApplicationScope(this)
    }

    lateinit var installInitialDataInteractor: InstallInitialDataInteractor

    override fun onCreate() {
        super.onCreate()
        ApplicationCreationScope(this).inject()

        installInitialDataInteractor.installIfNotInstalled().subscribe()
    }
}