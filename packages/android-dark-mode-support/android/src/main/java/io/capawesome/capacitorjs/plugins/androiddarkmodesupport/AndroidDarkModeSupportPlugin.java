package io.capawesome.capacitorjs.plugins.androiddarkmodesupport;

import android.content.res.Configuration;
import com.getcapacitor.Plugin;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "AndroidDarkModeSupport")
public class AndroidDarkModeSupportPlugin extends Plugin {

    private AndroidDarkModeSupport implementation;

    @Override
    public void load() {
        implementation = new AndroidDarkModeSupport(getContext(), getBridge());
        implementation.syncDarkMode();
    }

    @Override
    public void handleOnConfigurationChanged(Configuration newConfig) {
        super.handleOnConfigurationChanged(newConfig);
        implementation.syncDarkMode();
    }
}
