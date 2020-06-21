package com.methodsignature.simplehomescreen.apps

import androidx.lifecycle.ViewModelProviders
import com.methodsignature.simplehomescreen.Application
import com.methodsignature.simplehomescreen.ApplicationScope
import com.methodsignature.simplehomescreen.interactors.GetAllLaunchableActivitiesInteractor
import com.methodsignature.simplehomescreen.launch.AppLauncher
import com.methodsignature.simplehomescreen.launch.DefaultAppLauncher

class ViewAllAppsActivityScope(private val activity: ViewAllAppsActivity) {

    private val applicationScope: ApplicationScope by lazy {
        Application.scopeFrom(activity)
    }

    private val interactor: GetAllLaunchableActivitiesInteractor by lazy {
        GetAllLaunchableActivitiesInteractor(applicationScope.launchableActivityStore)
    }

    private val presenter: ViewAllAppsPresenter by lazy {
        ViewAllAppsPresenter(
            interactor,
            appLauncher,
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

    private val appLauncher: AppLauncher by lazy {
        DefaultAppLauncher(activity)
    }
}