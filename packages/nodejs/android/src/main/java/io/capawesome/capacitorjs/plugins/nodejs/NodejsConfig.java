package io.capawesome.capacitorjs.plugins.nodejs;

import androidx.annotation.NonNull;

public class NodejsConfig {

    @NonNull
    private String nodeDir = "nodejs";

    @NonNull
    private String startMode = "auto";

    @NonNull
    public String getNodeDir() {
        return nodeDir;
    }

    public void setNodeDir(@NonNull String nodeDir) {
        this.nodeDir = nodeDir;
    }

    @NonNull
    public String getStartMode() {
        return startMode;
    }

    public void setStartMode(@NonNull String startMode) {
        this.startMode = startMode;
    }
}
