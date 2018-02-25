package com.methodsignature.simplehomescreen.android

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import io.reactivex.Observable
import java.util.Collections

interface ResolveInfoExtractor {
    fun extract(): Observable<List<ResolveInfo>>
}

class DefaultResolveInfoExtractor(private val packageManager: PackageManager) : ResolveInfoExtractor {
    override fun extract(): Observable<List<ResolveInfo>> {
        return Observable.just(packageManager)
            .map {
                val launcherIntent = Intent(Intent.ACTION_MAIN, null)
                    .addCategory(Intent.CATEGORY_LAUNCHER)
                packageManager.queryIntentActivities(launcherIntent, 0)
            }
            .map {
                Collections.sort(it, ResolveInfo.DisplayNameComparator(packageManager))
                it
            }
    }
}