package io.capawesome.capacitorjs.plugins.permissions.interfaces;

import androidx.annotation.NonNull;

public interface NonEmptyResultCallback<T extends Result> extends Callback {
    void success(@NonNull T result);
}
