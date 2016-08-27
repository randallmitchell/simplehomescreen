package com.methodsignature.simplehomescreen.data;

import android.content.ComponentName;
import android.graphics.drawable.Drawable;

/**
 * Created by randallmitchell on 7/20/16.
 */
public class Launchable {

    private ComponentName componentName;
    private String appName;
    private String packageName;
    private Drawable icon;

    public Launchable(ComponentName componentName, String appName, String packageName, Drawable icon) {
        this.componentName = componentName;
        this.appName = appName;
        this.packageName = packageName;
        this.icon = icon;
    }

    public ComponentName getComponentName(){
        return componentName;
    }

    public String getAppName() {
        return appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public Drawable getIcon() {
        return icon;
    }
}
