package com.methodsignature.simplehomescreen

import com.methodsignature.simplehomescreen.android.packagemanager.ApplicationUpdateBroadcastReceiver
import com.methodsignature.simplehomescreen.android.packagemanager.DefaultResolveInfoExtractor
import com.methodsignature.simplehomescreen.android.packagemanager.ResolveInfoExtractor
import com.methodsignature.simplehomescreen.interactors.ApplicationInstalledInteractor
import com.methodsignature.simplehomescreen.interactors.ApplicationUninstalledInteractor
import com.methodsignature.simplehomescreen.interactors.InstallInitialDataInteractor

class ApplicationCreationScope(private val application: Application) {

    private val applicationScope: ApplicationScope by lazy {
        Application.scope()
    }

    private val installInitialDataInteractor: InstallInitialDataInteractor by lazy {
        InstallInitialDataInteractor(
            applicationScope.settingsStore,
            resolveInfoExtractor,
            applicationScope.launchableActivityStore,
            application.packageManager
        )
    }

    private val resolveInfoExtractor: ResolveInfoExtractor by lazy {
        DefaultResolveInfoExtractor(application.packageManager)
    }

    fun inject() {
        application.installInitialDataInteractor = installInitialDataInteractor
        application.applicationUpdateBroadcastReceiver = mApplicationUpdateBroadcastReceiver
    }

    private val mApplicationUpdateBroadcastReceiver: ApplicationUpdateBroadcastReceiver by lazy {
        ApplicationUpdateBroadcastReceiver(
            applicationInstalledInteractor,
            applicationUninstalledInteractor
        )
    }

    private val applicationInstalledInteractor: ApplicationInstalledInteractor by lazy {
        ApplicationInstalledInteractor(
            applicationScope.launchableActivityStore,
            application.packageManager
        )
    }

    private val applicationUninstalledInteractor: ApplicationUninstalledInteractor by lazy {
        ApplicationUninstalledInteractor(applicationScope.launchableActivityStore)
    }
}