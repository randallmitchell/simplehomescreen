package com.methodsignature.simplehomescreen

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.methodsignature.simplehomescreen.android.packagemanager.ApplicationUpdateBroadcastReceiver
import com.methodsignature.simplehomescreen.interactors.InstallInitialDataInteractor

class Application: android.app.Application() {

    companion object {

        private lateinit var application: Application

        fun instance(): Application {
            return application
        }

        fun scope(): ApplicationScope {
            return instance().applicationScope
        }

        @Deprecated(
            message = "Context no longer required",
            replaceWith = ReplaceWith("instance()")
        )
        fun from(context: Context): Application {
            return context.applicationContext as Application
        }

        @Deprecated(
            message = "Context no longer required",
            replaceWith = ReplaceWith("scope()")
        )
        fun scopeFrom(context:Context): ApplicationScope {
            return instance().applicationScope
        }
    }

    private val applicationScope: ApplicationScope by lazy {
        ApplicationScope(this)
    }

    lateinit var installInitialDataInteractor: InstallInitialDataInteractor

    lateinit var applicationUpdateBroadcastReceiver: ApplicationUpdateBroadcastReceiver

    override fun onCreate() {
        application = this
        super.onCreate()
        ApplicationCreationScope(this).inject()

        installInitialDataInteractor.installIfNotInstalled().subscribe(
            {},
            { Log.e("Application", "Error retrieving initial app list.", it) }
        )

        registerReceiver(
            applicationUpdateBroadcastReceiver,
            IntentFilter().apply {
                addAction(Intent.ACTION_PACKAGE_REMOVED)
                addAction(Intent.ACTION_PACKAGE_ADDED)
                addDataScheme("package")
            }
        )
    }
}