package io.capawesome.capacitorjs.plugins.settingslauncher.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.settingslauncher.classes.CustomExceptions;

public class OpenAndroidSettingsOptions {

    @NonNull
    private final String page;

    public OpenAndroidSettingsOptions(@NonNull PluginCall call) throws Exception {
        String page = call.getString("page");
        if (page == null) {
            throw CustomExceptions.PAGE_MISSING;
        }
        this.page = page;
    }

    @NonNull
    public String getPage() {
        return page;
    }
}
