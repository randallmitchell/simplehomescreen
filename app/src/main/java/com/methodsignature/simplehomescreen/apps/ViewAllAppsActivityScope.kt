package com.methodsignature.simplehomescreen.apps

import android.arch.lifecycle.ViewModelProviders
import com.methodsignature.simplehomescreen.Application
import com.methodsignature.simplehomescreen.ApplicationScope
import com.methodsignature.simplehomescreen.interactors.GetAllLaunchableActivities
import com.methodsignature.simplehomescreen.interactors.LaunchExternalActivity
import com.methodsignature.simplehomescreen.interactors.LaunchExternalApplicationSettings

class ViewAllAppsActivityScope(private val activity: ViewAllAppsActivity) {

    private val applicationScope: ApplicationScope by lazy {
        Application.scopeFrom(activity)
    }

    private val interactor: GetAllLaunchableActivities by lazy {
        GetAllLaunchableActivities(applicationScope.launchableActivityStore)
    }

    private val presenter: ViewAllAppsPresenter by lazy {
        ViewAllAppsPresenter(
            interactor,
            launchExternalActivity,
            launchExternalApplicationSettings,
            viewModel,
            activity.view
        )
    }

    fun inject() {
        activity.presenter = presenter
    }

    private val viewModel: AppListViewModel by lazy {
        ViewModelProviders.of(activity).get(AppListViewModel::class.java).init(activity)
    }

    private val launchExternalActivity: LaunchExternalActivity by lazy {
        LaunchExternalActivity(activity)
    }

    private val launchExternalApplicationSettings: LaunchExternalApplicationSettings by lazy {
        LaunchExternalApplicationSettings(activity)
    }
}