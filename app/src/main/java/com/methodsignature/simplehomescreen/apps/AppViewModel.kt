package com.methodsignature.simplehomescreen.apps

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import android.content.ComponentName
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

data class AppViewModel(
    val readableName: String,
    val componentName: ComponentName
)

class AppListViewModel : ViewModel() {

    private val appList: MutableLiveData<List<AppViewModel>> = MutableLiveData()
    private val appListSubject: Subject<List<AppViewModel>> = PublishSubject.create()

    fun init(lifecycleOwner: LifecycleOwner): AppListViewModel {
        appList.observe(lifecycleOwner, Observer<List<AppViewModel>> { it?.apply { appListSubject.onNext(it) } })
        return this
    }

    fun consume(): Consumer<List<AppViewModel>> {
        return Consumer { appList.postValue(it) }
    }

    fun observe(): Observable<List<AppViewModel>> {
        return appListSubject
    }
}
