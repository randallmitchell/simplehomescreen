package com.methodsignature.simplehomescreen.interactors

import com.methodsignature.simplehomescreen.launchable.LaunchableActivityStore
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class ApplicationUninstalledInteractor(private val launchableActivityStore: LaunchableActivityStore) {

    fun removeByPackageName(packageName: String): Completable {
        return Completable.fromCallable {
            launchableActivityStore.deleteByPackage(packageName)
        }.subscribeOn(Schedulers.io())
    }
}