package com.methodsignature.simplehomescreen.interactors

import android.content.pm.PackageManager
import com.methodsignature.simplehomescreen.android.ResolveInfoExtractor
import com.methodsignature.simplehomescreen.launchable.LaunchableActivity
import com.methodsignature.simplehomescreen.launchable.LaunchableActivityStore
import com.methodsignature.simplehomescreen.launchable.add
import com.methodsignature.simplehomescreen.settings.SettingsStore
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

/**
 * Data:
 *    None required
 *
 * Primary Course:
 *     1. User navigates to the Android home screen.
 *     2. System populates database if this is user's first home screen visit.
 */
class InstallInitialData(
    private val settingsStore: SettingsStore,
    private val resolveInfoExtractor: ResolveInfoExtractor,
    private val launchableActivityStore: LaunchableActivityStore,
    private val packageManager: PackageManager
) {
    fun installIfNotInstalled(): Completable {
        return settingsStore.initialDataInstalled()
            .subscribeOn(Schedulers.io())
            .flatMapCompletable {
                if (it) {
                    Completable.complete()
                } else {
                    resolveInfoExtractor.extract()
                        .flatMapIterable { it }
                        .map {
                            LaunchableActivity(
                                it.activityInfo.packageName,
                                it.activityInfo.name,
                                it.loadLabel(packageManager).toString()
                            )
                        }
                        .toList()
                        .flatMapCompletable {
                            launchableActivityStore.add(it)
                        }
                        .andThen(settingsStore.setInitialDataRetrieved(true))
                }
            }
    }
}