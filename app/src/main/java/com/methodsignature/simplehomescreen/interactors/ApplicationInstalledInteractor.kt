package com.methodsignature.simplehomescreen.interactors

import android.content.pm.PackageManager
import com.methodsignature.simplehomescreen.launchable.LaunchableActivity
import com.methodsignature.simplehomescreen.launchable.LaunchableActivityStore
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class ApplicationInstalledInteractor(
    private val launchableActivityStore: LaunchableActivityStore,
    private val packageManager: PackageManager
) {

    fun addByPackageIfLaunchable(packageName: String): Completable {
        return Completable.fromCallable {
            packageManager.getLaunchIntentForPackage(packageName).let { launchIntent ->
                packageManager.getApplicationInfo(packageName, 0).let { appInfo ->
                    packageManager.getApplicationLabel(appInfo)?.let { appLabel ->
                        launchIntent?.component?.className?.let { className ->
                            launchableActivityStore.synchronousAddAll(
                                LaunchableActivity(
                                    packageName,
                                    className,
                                    appLabel.toString()
                                )
                            )

                        }
                    }
                }
            }
        }.subscribeOn(Schedulers.io())
    }
}
