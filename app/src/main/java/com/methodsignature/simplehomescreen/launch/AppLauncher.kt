package com.methodsignature.simplehomescreen.launch

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import io.reactivex.Completable

interface AppLauncher {
    fun launchAppComponent(componentName: ComponentName): Completable
    fun launchAppSettings(packageName: String): Completable
}

class DefaultAppLauncher(private val fromActivity: Activity): AppLauncher {

    override fun launchAppComponent(componentName: ComponentName): Completable {
        return Completable.fromAction {
            val intent = Intent()
                .setAction(Intent.ACTION_MAIN)
                .addCategory(Intent.CATEGORY_LAUNCHER)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                .setComponent(componentName)
            fromActivity.startActivity(intent)
        }
    }

    override fun launchAppSettings(packageName: String): Completable {
        return Completable.fromAction {
            val intent = Intent()
                .setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.parse("package:" + packageName))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            fromActivity.startActivity(intent)
        }
    }
}