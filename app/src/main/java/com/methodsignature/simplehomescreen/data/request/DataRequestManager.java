package com.methodsignature.simplehomescreen.data.request;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.methodsignature.simplehomescreen.data.Launchable;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.functions.Func1;

/**
 * This is the main facade around the Android APIs for finding and retrieving apps.
 *
 * This class could be broken out into individual Observables (command style).  In the future
 * there may be a need to maintain local state, so leaving it here for now.
 *
 * Created by randallmitchell on 7/20/16.
 */
public class DataRequestManager {

    private PackageManager packageManager;

    public DataRequestManager(PackageManager packageManager) {
        this.packageManager= packageManager;
    }

    public Observable<Launchable> getLaunchableApps() {
        return Observable.fromCallable(new Callable<PackageManager>() {
                    @Override
                    public PackageManager call() throws Exception {
                        return packageManager;
                    }
                })

                .flatMap(new Func1<PackageManager, Observable<Launchable>>() {
                    @Override
                    public Observable<Launchable> call(final PackageManager packageManager) {
                        Intent mainLauncherIntent = new Intent(Intent.ACTION_MAIN, null);
                        mainLauncherIntent.addCategory(Intent.CATEGORY_LAUNCHER);

                        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(mainLauncherIntent, 0);
                        Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(packageManager));

                        return Observable.from(resolveInfos)
                                .filter(new Func1<ResolveInfo, Boolean>() {
                                    @Override
                                    public Boolean call(ResolveInfo resolveInfo) {
                                        return resolveInfo.activityInfo != null;
                                    }
                                })
                                .flatMap(new Func1<ResolveInfo, Observable<Launchable>>() {
                                    @Override
                                    public Observable<Launchable> call(final ResolveInfo resolveInfo) {
                                        return Observable.just(new Launchable(
                                                new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name),
                                                resolveInfo.loadLabel(packageManager).toString(),
                                                resolveInfo.activityInfo.loadIcon(packageManager)));
                                    }
                                });
                    }
                });
    }
}
