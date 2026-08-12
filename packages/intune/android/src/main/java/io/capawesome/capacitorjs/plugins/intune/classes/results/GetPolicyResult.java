package io.capawesome.capacitorjs.plugins.intune.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.intune.interfaces.Result;

public class GetPolicyResult implements Result {

    private final boolean contactSyncAllowed;
    private final boolean fileEncryptionRequired;
    private final boolean managedBrowserRequired;
    private final boolean pinRequired;
    private final boolean saveToPersonalStorageAllowed;
    private final boolean screenCaptureAllowed;

    public GetPolicyResult(
        boolean contactSyncAllowed,
        boolean fileEncryptionRequired,
        boolean managedBrowserRequired,
        boolean pinRequired,
        boolean saveToPersonalStorageAllowed,
        boolean screenCaptureAllowed
    ) {
        this.contactSyncAllowed = contactSyncAllowed;
        this.fileEncryptionRequired = fileEncryptionRequired;
        this.managedBrowserRequired = managedBrowserRequired;
        this.pinRequired = pinRequired;
        this.saveToPersonalStorageAllowed = saveToPersonalStorageAllowed;
        this.screenCaptureAllowed = screenCaptureAllowed;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("contactSyncAllowed", contactSyncAllowed);
        result.put("fileEncryptionRequired", fileEncryptionRequired);
        result.put("managedBrowserRequired", managedBrowserRequired);
        result.put("pinRequired", pinRequired);
        result.put("saveToPersonalStorageAllowed", saveToPersonalStorageAllowed);
        result.put("screenCaptureAllowed", screenCaptureAllowed);
        return result;
    }
}
