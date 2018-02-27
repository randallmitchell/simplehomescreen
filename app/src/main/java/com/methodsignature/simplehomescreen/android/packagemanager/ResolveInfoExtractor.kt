package com.methodsignature.simplehomescreen.android.packagemanager

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import io.reactivex.Single
import java.util.*

interface ResolveInfoExtractor {
    fun extract(): Single<List<ResolveInfo>>
}

class DefaultResolveInfoExtractor(private val packageManager: PackageManager) :
    ResolveInfoExtractor {
    override fun extract(): Single<List<ResolveInfo>> {
        return Single.just(packageManager)
            .map {
                val launcherIntent = Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER)
                val list = packageManager.queryIntentActivities(launcherIntent, 0)
                Collections.sort(list, ResolveInfo.DisplayNameComparator(packageManager))
                list
            }
    }
}