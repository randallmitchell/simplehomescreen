package com.methodsignature.simplehomescreen.settings

import android.content.SharedPreferences
import io.reactivex.Completable
import io.reactivex.Single

class SettingsStore(private val sharedPreferences: SharedPreferences) {

    fun initialDataInstalled(): Single<Boolean> {
        return Single.fromCallable {
            sharedPreferences.getBoolean(KEY_INITIAL_DATA_INSTALLED, false)
        }
    }

    fun setInitialDataRetrieved(initialDataInstalled: Boolean): Completable {
        return Completable.fromCallable {
            sharedPreferences.edit().putBoolean(KEY_INITIAL_DATA_INSTALLED, initialDataInstalled).apply()
        }
    }

    companion object {
        const val KEY_INITIAL_DATA_INSTALLED = "initial_data_installed"
    }
}