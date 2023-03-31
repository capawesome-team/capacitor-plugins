package io.capawesome.capacitorjs.plugins.managedconfigurations;

import android.content.RestrictionsManager;
import android.os.Bundle;
import com.getcapacitor.Bridge;

public class ManagedConfigurations {

    private RestrictionsManager myRestrictionsMgr;
    private Bundle appRestrictions;

    ManagedConfigurations(Bridge bridge) {
        myRestrictionsMgr = (RestrictionsManager) bridge.getActivity().getSystemService(bridge.getContext().RESTRICTIONS_SERVICE);
        this.refreshApplicationRestrictions();
    }

    public void refreshApplicationRestrictions() {
        appRestrictions = myRestrictionsMgr.getApplicationRestrictions();
    }

    public String getString(String key) {
        if (appRestrictions.containsKey(key)) {
            return appRestrictions.getString(key);
        }
        return null;
    }

    public Integer getInteger(String key) {
        if (appRestrictions.containsKey(key)) {
            return appRestrictions.getInt(key);
        }
        return null;
    }

    public Boolean getBoolean(String key) {
        if (appRestrictions.containsKey(key)) {
            return appRestrictions.getBoolean(key);
        }
        return null;
    }
}
