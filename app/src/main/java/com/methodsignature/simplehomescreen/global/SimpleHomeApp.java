package com.methodsignature.simplehomescreen.global;

import android.app.Application;

import com.methodsignature.simplehomescreen.data.request.DataRequestManagerModule;

import timber.log.Timber;

/**
 * Created by randallmitchell on 7/20/16.
 */
public class SimpleHomeApp extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        applicationComponent = DaggerApplicationComponent.builder()
                .dataRequestManagerModule(new DataRequestManagerModule(getPackageManager()))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
