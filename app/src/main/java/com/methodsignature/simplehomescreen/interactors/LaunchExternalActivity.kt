package com.methodsignature.simplehomescreen.interactors

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import io.reactivex.Completable

/**
 * Data:
 *     ComponentName - The Android [ComponentName] that should be launched.
 *
 * Primary Course:
 *     1. User taps on Activity item on home screen.
 *     2. System navigates user to the clicked Activity.
 */
class LaunchExternalActivity(private val fromActivity: Activity) {

    fun launch(componentName: ComponentName): Completable {
        return Completable.fromAction {
            val intent = Intent()
                .setAction(Intent.ACTION_MAIN)
                .addCategory(Intent.CATEGORY_LAUNCHER)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                .setComponent(componentName)
            fromActivity.startActivity(intent)
        }
    }
}