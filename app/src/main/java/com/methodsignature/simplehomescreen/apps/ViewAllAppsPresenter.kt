package com.methodsignature.simplehomescreen.apps

import android.content.ComponentName
import com.methodsignature.simplehomescreen.interactors.GetAllLaunchableActivities
import com.methodsignature.simplehomescreen.interactors.LaunchExternalActivity
import com.methodsignature.simplehomescreen.interactors.LaunchExternalApplicationSettings
import com.methodsignature.simplehomescreen.mvp.Presenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable


class ViewAllAppsPresenter(
    private val getLaunchablesInteractor: GetAllLaunchableActivities,
    private val launchExternalActivityInteractor: LaunchExternalActivity,
    private val launchExternalApplicationSettings: LaunchExternalApplicationSettings,
    private val viewModel: AppListViewModel,
    private val view: ViewAllAppsView
) : Presenter {

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun create() {
        disposables.add(
            view.onAppClickedId()
                .flatMapCompletable { launchExternalActivityInteractor.launch(it.componentName) }
                .subscribe()
        )

        disposables.add(
            view.onAppLongClickedId()
                .flatMapCompletable { launchExternalApplicationSettings.launch(it.componentName.packageName) }
                .subscribe()
        )

        disposables.add(
            viewModel.observe().subscribe(view.display())
        )

        disposables.add(
            getLaunchablesInteractor.get()
                .flatMap {
                    Observable.fromIterable(it)
                        .map { AppViewModel(it.readableName, ComponentName(it.packageName, it.activityName)) }
                        .toList()
                        .toObservable()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(viewModel.consume())
        )
    }

    override fun destroy() {
        disposables.clear()
    }
}
