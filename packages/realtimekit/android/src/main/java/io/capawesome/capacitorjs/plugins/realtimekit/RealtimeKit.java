package io.capawesome.capacitorjs.plugins.realtimekit;

import android.util.Log;

public class RealtimeKit {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }
}
