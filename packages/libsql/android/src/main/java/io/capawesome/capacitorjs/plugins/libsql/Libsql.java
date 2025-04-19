package io.capawesome.capacitorjs.plugins.libsql;

import android.util.Log;

public class Libsql {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }
}
