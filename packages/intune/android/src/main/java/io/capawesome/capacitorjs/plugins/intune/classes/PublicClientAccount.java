package io.capawesome.capacitorjs.plugins.intune.classes;

import androidx.annotation.NonNull;
import com.microsoft.identity.client.IAccount;
import com.microsoft.identity.client.IMultipleAccountPublicClientApplication;

public class PublicClientAccount {

    @NonNull
    private final IAccount account;

    @NonNull
    private final IMultipleAccountPublicClientApplication application;

    public PublicClientAccount(@NonNull IMultipleAccountPublicClientApplication application, @NonNull IAccount account) {
        this.application = application;
        this.account = account;
    }

    @NonNull
    public IAccount getAccount() {
        return account;
    }

    @NonNull
    public IMultipleAccountPublicClientApplication getApplication() {
        return application;
    }
}
