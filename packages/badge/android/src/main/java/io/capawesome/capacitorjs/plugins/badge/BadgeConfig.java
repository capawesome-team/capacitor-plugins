package io.capawesome.capacitorjs.plugins.badge;

public class BadgeConfig {

    private boolean persist = true;
    private boolean autoClear = false;

    public boolean getPersist() {
        return persist;
    }

    public void setPersist(boolean persist) {
        this.persist = persist;
    }

    public boolean getAutoClear() {
        return autoClear;
    }

    public void setAutoClear(boolean autoClear) {
        this.autoClear = autoClear;
    }
}
