package io.capawesome.capacitorjs.plugins.pixlive.interfaces;

import androidx.annotation.NonNull;

public interface NonEmptyCallback<T> extends Callback {
    void success(@NonNull T result);
}
