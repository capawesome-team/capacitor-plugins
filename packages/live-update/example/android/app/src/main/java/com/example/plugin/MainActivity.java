package com.example.plugin;

import android.content.pm.PackageManager;
import android.os.Bundle;

import com.getcapacitor.BridgeActivity;
import com.getcapacitor.Logger;

import io.capawesome.capacitorjs.plugins.liveupdate.LiveUpdate;
import io.capawesome.capacitorjs.plugins.liveupdate.LiveUpdateConfig;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.NonEmptyCallback;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Additional initialization code can go here
        try {
            LiveUpdate instance = LiveUpdate.getInstance(this, getBridge(), new LiveUpdateConfig(), null);
            NonEmptyCallback<Result> callback = new NonEmptyCallback<>() {
                @Override
                public void success(Result result) {
                    Logger.info("Live Update Version Code: " + result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    Logger.error("Error", exception);
                }
            };
            instance.getVersionCode(callback);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
