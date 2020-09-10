package com.egls.demo;

import android.app.Application;

import com.egls.platform.components.EglsPlatform;

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EglsPlatform.initApplication(this);
    }
}
