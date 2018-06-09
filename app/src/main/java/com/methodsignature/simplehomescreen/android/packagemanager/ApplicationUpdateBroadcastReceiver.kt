package com.methodsignature.simplehomescreen.android.packagemanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.methodsignature.simplehomescreen.interactors.ApplicationInstalledInteractor
import com.methodsignature.simplehomescreen.interactors.ApplicationUninstalledInteractor
import io.reactivex.android.schedulers.AndroidSchedulers

class ApplicationUpdateBroadcastReceiver(
    private val applicationInstalledInteractor: ApplicationInstalledInteractor,
    private val applicationUninstalledInteractor: ApplicationUninstalledInteractor
): BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (!intent.extras.getBoolean(Intent.EXTRA_REPLACING, false)) {

            when (intent.action) {
                Intent.ACTION_PACKAGE_ADDED -> {
                    val packageName = intent.data.schemeSpecificPart
                    applicationInstalledInteractor.addByPackageIfLaunchable(packageName)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            {},
                            {
                                Log.e(
                                    ApplicationUpdateBroadcastReceiver::class.java.simpleName,
                                    "Failed to add installed application",
                                    it
                                )
                            }
                        )
                }

                Intent.ACTION_PACKAGE_REMOVED -> {
                    val packageName = intent.data.schemeSpecificPart
                    applicationUninstalledInteractor.removeByPackageName(packageName)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            {},
                            {
                                Log.e(
                                    ApplicationUpdateBroadcastReceiver::class.java.simpleName,
                                    "Failed to remove uninstalled application",
                                    it
                                )
                            }
                        )
                }
            }
        }
    }
}