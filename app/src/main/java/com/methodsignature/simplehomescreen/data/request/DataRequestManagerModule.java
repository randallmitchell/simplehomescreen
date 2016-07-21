package com.methodsignature.simplehomescreen.data.request;

import android.content.pm.PackageManager;

import com.methodsignature.simplehomescreen.data.request.DataRequestManager;
import com.methodsignature.simplehomescreen.scopes.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by randallmitchell on 7/20/16.
 */
@Module
public class DataRequestManagerModule {

    private PackageManager packageManager;

    public DataRequestManagerModule(PackageManager packageManager) {
        this.packageManager = packageManager;
    }

    @Provides
    @ApplicationScope
    public DataRequestManager provideAppStore(PackageManager packageManager) {
        return new DataRequestManager(packageManager);
    }

    @Provides
    @ApplicationScope
    public PackageManager providePackageManager() {
        return packageManager;
    }
}
