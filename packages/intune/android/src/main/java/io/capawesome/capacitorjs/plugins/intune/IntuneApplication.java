package io.capawesome.capacitorjs.plugins.intune;

import android.app.Application;

public class IntuneApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Intune.initialize(this);
    }
}
