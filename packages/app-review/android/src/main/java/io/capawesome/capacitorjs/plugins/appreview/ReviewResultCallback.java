package io.capawesome.capacitorjs.plugins.appreview;

public interface ReviewResultCallback<T> {
    void success(T result);

    void error(Exception exception);
}
