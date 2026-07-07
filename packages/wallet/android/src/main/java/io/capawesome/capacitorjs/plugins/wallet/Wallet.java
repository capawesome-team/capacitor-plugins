package io.capawesome.capacitorjs.plugins.wallet;

import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import io.capawesome.capacitorjs.plugins.wallet.classes.options.SaveToGoogleWalletOptions;
import io.capawesome.capacitorjs.plugins.wallet.interfaces.EmptyCallback;

public class Wallet {

    private static final String GOOGLE_WALLET_SAVE_URL = "https://pay.google.com/gp/v/save/";

    @NonNull
    private final WalletPlugin plugin;

    public Wallet(@NonNull WalletPlugin plugin) {
        this.plugin = plugin;
    }

    public void saveToGoogleWallet(@NonNull SaveToGoogleWalletOptions options, @NonNull EmptyCallback callback) {
        try {
            Uri uri = Uri.parse(GOOGLE_WALLET_SAVE_URL + options.getJwt());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            plugin.getActivity().startActivity(intent);
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }
}
