package com.methodsignature.simplehomescreen

import android.content.pm.PackageManager
import com.methodsignature.simplehomescreen.android.DefaultResolveInfoExtractor
import com.methodsignature.simplehomescreen.android.ResolveInfoExtractor
import com.methodsignature.simplehomescreen.interactors.InstallInitialData
import com.methodsignature.simplehomescreen.launchable.LaunchableActivityStore
import com.methodsignature.simplehomescreen.settings.SettingsStore

class ApplicationCreationScope(
    private val settingsStore: SettingsStore,
    private val launchableActivityStore: LaunchableActivityStore,
    private val packageManager: PackageManager
) {

    val installInitialData: InstallInitialData by lazy {
        InstallInitialData(settingsStore, resolveInfoExtractor, launchableActivityStore, packageManager)
    }

    private val resolveInfoExtractor: ResolveInfoExtractor by lazy {
        DefaultResolveInfoExtractor(packageManager)
    }
}