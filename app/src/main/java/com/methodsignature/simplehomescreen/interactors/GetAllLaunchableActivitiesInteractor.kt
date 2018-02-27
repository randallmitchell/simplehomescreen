package com.methodsignature.simplehomescreen.interactors

import com.methodsignature.simplehomescreen.launchable.LaunchableActivity
import com.methodsignature.simplehomescreen.launchable.LaunchableActivityStore
import io.reactivex.Observable

/**
 * Data:
 *     None required
 *
 * Primary Course:
 *     1. User navigates to the Android home screen.
 *     2. System delivers all launchable home intents (apps) to the user.
 * Exception Course:
 *     1. System delivers error to the user.
 */
class GetAllLaunchableActivitiesInteractor(private val activityStore: LaunchableActivityStore) {
    fun get(): Observable<List<LaunchableActivity>> {
        return activityStore.getAll().toObservable()
    }
}