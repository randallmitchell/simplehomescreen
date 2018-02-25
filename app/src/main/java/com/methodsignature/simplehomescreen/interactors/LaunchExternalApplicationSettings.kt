package com.methodsignature.simplehomescreen.interactors

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import io.reactivex.Completable

/**
 * Data:
 *     ComponentName - The Android [ComponentName] that should be launched.
 *
 * Primary Course:
 *     1. User taps on Activity item on home screen.
 *     2. System navigates user to the clicked Activity.
 */
class LaunchExternalApplicationSettings(private val fromActivity: Activity) {

    fun launch(packageName: String): Completable {
        return Completable.fromAction {
            val intent = Intent()
                .setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.parse("package:" + packageName))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            fromActivity.startActivity(intent)
        }
    }
}