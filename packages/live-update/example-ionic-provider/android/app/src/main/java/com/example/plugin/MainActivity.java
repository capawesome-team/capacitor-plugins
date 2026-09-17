package com.example.plugin;

import android.os.Bundle;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        registerPlugin(IonicProviderTestPlugin.class);
        super.onCreate(savedInstanceState);
    }
}
