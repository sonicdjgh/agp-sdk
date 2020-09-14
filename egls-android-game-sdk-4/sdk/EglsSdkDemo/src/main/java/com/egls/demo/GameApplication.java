package com.egls.demo;

import android.app.Application;

import com.egls.platform.components.EglsPlatform;
import com.egls.support.components.EglsTracker;

public class GameApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EglsTracker.initApplication(this);
        EglsPlatform.initApplication(this);
    }
}
