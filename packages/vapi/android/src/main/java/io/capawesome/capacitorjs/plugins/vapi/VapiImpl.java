package io.capawesome.capacitorjs.plugins.vapi;

import io.capawesome.capacitorjs.plugins.vapi.classes.options.SetupOptions;
import ai.vapi.android.Vapi;

public class VapiImpl {
    private final VapiPlugin plugin;

    public VapiImpl(VapiPlugin plugin) {
        this.plugin = plugin;
    }

    public void setup(SetupOptions options) {
        String apiKey = options.getApiKey();

        Vapi.Configuration configuration = new Vapi.Configuration(apiKey, "api.vapi.ai");
        Vapi vapi = new Vapi(plugin.getContext(), plugin.getActivity().getLifecycle(), configuration);
    }
}
