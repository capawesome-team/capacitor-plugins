package io.capawesome.capacitorjs.plugins.datetimepicker;

import androidx.annotation.Nullable;
import java.util.Date;

public class PresentResultCallback {

    interface SuccessListener {
        void onSuccess(Date date);
    }

    interface CancelListener {
        void onCancel();
    }

    interface DismissListener {
        void onDismiss();
    }

    @Nullable
    private SuccessListener successListener;

    @Nullable
    private CancelListener cancelListener;

    @Nullable
    private DismissListener dismissListener;

    public void setSuccessListener(@Nullable SuccessListener listener) {
        this.successListener = listener;
    }

    public void setCancelListener(@Nullable CancelListener listener) {
        this.cancelListener = listener;
    }

    public void setDismissListener(@Nullable DismissListener listener) {
        this.dismissListener = listener;
    }

    public void success(Date date) {
        if (successListener != null) {
            successListener.onSuccess(date);
        }
        this.removeAllListener();
    }

    public void cancel() {
        if (cancelListener != null) {
            cancelListener.onCancel();
        }
        this.removeAllListener();
    }

    public void dismiss() {
        if (dismissListener != null) {
            dismissListener.onDismiss();
        }
        this.removeAllListener();
    }

    private void removeAllListener() {
        successListener = null;
        cancelListener = null;
        dismissListener = null;
    }
}
