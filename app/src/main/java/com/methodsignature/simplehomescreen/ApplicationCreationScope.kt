package com.methodsignature.simplehomescreen

import com.methodsignature.simplehomescreen.android.packagemanager.DefaultResolveInfoExtractor
import com.methodsignature.simplehomescreen.android.packagemanager.ResolveInfoExtractor
import com.methodsignature.simplehomescreen.interactors.InstallInitialDataInteractor

class ApplicationCreationScope(private val application: Application) {

    private val applicationScope: ApplicationScope by lazy {
        Application.scopeFrom(application)
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
    }
}