package io.capawesome.capacitorjs.plugins.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.network.classes.results.GetStatusResult;
import io.capawesome.capacitorjs.plugins.network.classes.results.IsAirplaneModeEnabledResult;
import io.capawesome.capacitorjs.plugins.network.interfaces.NonEmptyResultCallback;

public class Network {

    private static final String CONNECTION_TYPE_CELLULAR = "CELLULAR";
    private static final String CONNECTION_TYPE_ETHERNET = "ETHERNET";
    private static final String CONNECTION_TYPE_NONE = "NONE";
    private static final String CONNECTION_TYPE_UNKNOWN = "UNKNOWN";
    private static final String CONNECTION_TYPE_VPN = "VPN";
    private static final String CONNECTION_TYPE_WIFI = "WIFI";

    @NonNull
    private final NetworkPlugin plugin;

    @NonNull
    private final ConnectivityManager connectivityManager;

    private boolean isObserving = false;

    @Nullable
    private String lastStatusKey = null;

    private final ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NonNull android.net.Network network) {
            handleNetworkStatusChange();
        }

        @Override
        public void onCapabilitiesChanged(@NonNull android.net.Network network, @NonNull NetworkCapabilities capabilities) {
            handleNetworkStatusChange();
        }

        @Override
        public void onLost(@NonNull android.net.Network network) {
            handleNetworkStatusChange();
        }
    };

    public Network(@NonNull NetworkPlugin plugin) {
        this.plugin = plugin;
        this.connectivityManager = (ConnectivityManager) plugin.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public void getStatus(@NonNull NonEmptyResultCallback<GetStatusResult> callback) {
        callback.success(computeStatus());
    }

    public void isAirplaneModeEnabled(@NonNull NonEmptyResultCallback<IsAirplaneModeEnabledResult> callback) {
        boolean enabled = Settings.Global.getInt(plugin.getContext().getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        callback.success(new IsAirplaneModeEnabledResult(enabled));
    }

    public void startObserving() {
        if (isObserving) {
            return;
        }
        lastStatusKey = createStatusKey(computeStatus());
        connectivityManager.registerDefaultNetworkCallback(networkCallback);
        isObserving = true;
    }

    public void stopObserving() {
        if (!isObserving) {
            return;
        }
        connectivityManager.unregisterNetworkCallback(networkCallback);
        lastStatusKey = null;
        isObserving = false;
    }

    @NonNull
    private String computeConnectionType(@Nullable NetworkCapabilities capabilities) {
        if (capabilities == null) {
            return CONNECTION_TYPE_NONE;
        }
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
            return CONNECTION_TYPE_VPN;
        }
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            return CONNECTION_TYPE_WIFI;
        }
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            return CONNECTION_TYPE_CELLULAR;
        }
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            return CONNECTION_TYPE_ETHERNET;
        }
        return CONNECTION_TYPE_UNKNOWN;
    }

    @NonNull
    private GetStatusResult computeStatus() {
        android.net.Network network = connectivityManager.getActiveNetwork();
        NetworkCapabilities capabilities = network == null ? null : connectivityManager.getNetworkCapabilities(network);
        boolean connected = capabilities != null;
        String connectionType = computeConnectionType(capabilities);
        boolean internetReachable =
            capabilities != null &&
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
        return new GetStatusResult(connected, connectionType, internetReachable);
    }

    @NonNull
    private String createStatusKey(@NonNull GetStatusResult status) {
        return status.toJSObject().toString();
    }

    private void handleNetworkStatusChange() {
        GetStatusResult status = computeStatus();
        String statusKey = createStatusKey(status);
        if (statusKey.equals(lastStatusKey)) {
            return;
        }
        lastStatusKey = statusKey;
        plugin.notifyNetworkStatusChangeListeners(status);
    }
}
