package io.capawesome.capacitorjs.plugins.screenshot.classes.interfaces;

import androidx.annotation.NonNull;

public interface NonEmptyCallback<T> extends Callback {
    void success(@NonNull T result);
}
