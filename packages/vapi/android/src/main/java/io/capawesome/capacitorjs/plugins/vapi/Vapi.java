package io.capawesome.capacitorjs.plugins.vapi;

import android.util.Log;

public class Vapi {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }
}
