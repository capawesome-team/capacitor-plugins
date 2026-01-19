package io.capawesome.capacitorjs.plugins.squaremobilepayments.interfaces;

import androidx.annotation.NonNull;

public interface NonEmptyResultCallback<T extends Result> extends Callback {
    void success(@NonNull T result);
}
