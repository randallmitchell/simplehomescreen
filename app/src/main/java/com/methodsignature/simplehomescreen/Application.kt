package com.methodsignature.simplehomescreen

import android.content.Context

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

    override fun onCreate() {
        super.onCreate()

        val applicationCreationScope = ApplicationCreationScope(
            applicationScope.settingsStore,
            applicationScope.launchableActivityStore,
            packageManager)
        val installInitialData = applicationCreationScope.installInitialData
        installInitialData.installIfNotInstalled().subscribe()
    }
}