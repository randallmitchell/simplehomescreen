package com.methodsignature.simplehomescreen

import androidx.room.Room
import android.content.Context
import com.methodsignature.simplehomescreen.launchable.ApplicationDatabase
import com.methodsignature.simplehomescreen.launchable.LaunchableActivityStore
import com.methodsignature.simplehomescreen.settings.SettingsStore

class ApplicationScope(private val context: Context) {

    companion object {
        const val DATABASE_NAME = "database"
    }

    private val applicationDatabase: ApplicationDatabase by lazy {
        Room.databaseBuilder(context, ApplicationDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    val launchableActivityStore: LaunchableActivityStore by lazy {
        applicationDatabase.launchableActivityStore()
    }

    val settingsStore: SettingsStore by lazy {
        SettingsStore(context.getSharedPreferences("settings_store", 0))
    }
}