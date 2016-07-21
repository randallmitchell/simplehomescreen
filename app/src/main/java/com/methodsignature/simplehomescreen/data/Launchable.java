package com.methodsignature.simplehomescreen.data;

import android.content.ComponentName;
import android.graphics.drawable.Drawable;

/**
 * Created by randallmitchell on 7/20/16.
 */
public class Launchable {

    private ComponentName componentName;
    private String appName;
    private Drawable icon;

    public Launchable(ComponentName componentName, String appName, Drawable icon) {
        this.componentName = componentName;
        this.appName = appName;
        this.icon = icon;
    }

    public ComponentName getComponentName(){
        return componentName;
    }

    public String getAppName() {
        return appName;
    }

    public Drawable getIcon() {
        return icon;
    }
}
