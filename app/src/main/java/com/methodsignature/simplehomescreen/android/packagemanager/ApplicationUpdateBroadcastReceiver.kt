package com.methodsignature.simplehomescreen.android.packagemanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.methodsignature.simplehomescreen.interactors.ApplicationInstalledInteractor
import com.methodsignature.simplehomescreen.interactors.ApplicationUninstalledInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber

class ApplicationUpdateBroadcastReceiver(
    private val applicationInstalledInteractor: ApplicationInstalledInteractor,
    private val applicationUninstalledInteractor: ApplicationUninstalledInteractor
): BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.extras?.getBoolean(Intent.EXTRA_REPLACING, false) == true) {
            when (intent.action) {
                Intent.ACTION_PACKAGE_ADDED -> {
                    intent.data?.schemeSpecificPart?.let { packageName ->
                        applicationInstalledInteractor.addByPackageIfLaunchable(packageName)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                {},
                                {
                                    Timber.e(
                                        it,
                                        "Failed to add installed application"
                                    )
                                }
                            )
                    }
                }

                Intent.ACTION_PACKAGE_REMOVED -> {
                    intent.data?.schemeSpecificPart?.let { packageName ->
                        applicationUninstalledInteractor.removeByPackageName(packageName)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                {},
                                {
                                    Timber.e(
                                        it,
                                        "Failed to remove uninstalled application"
                                    )
                                }
                            )
                    }
                }
            }
        }
    }
}
