package com.methodsignature.simplehomescreen.global;

import android.app.Activity;

import com.methodsignature.simplehomescreen.scopes.ApplicationScope;
import com.methodsignature.simplehomescreen.data.request.DataRequestManager;
import com.methodsignature.simplehomescreen.data.request.DataRequestManagerModule;

import dagger.Component;

/**
 * Created by randallmitchell on 7/20/16.
 */
@Component(
        modules = {
                DataRequestManagerModule.class
        }
)
@ApplicationScope
public interface ApplicationComponent {
    void inject(Activity activity);

    DataRequestManager launchableStore();
}
