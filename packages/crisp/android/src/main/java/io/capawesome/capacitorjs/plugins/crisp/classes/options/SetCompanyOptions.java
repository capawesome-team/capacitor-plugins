package io.capawesome.capacitorjs.plugins.crisp.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.crisp.classes.CustomExceptions;

public class SetCompanyOptions {

    @Nullable
    private final String description;

    @Nullable
    private final String employmentRole;

    @Nullable
    private final String employmentTitle;

    @Nullable
    private final String geolocationCity;

    @Nullable
    private final String geolocationCountry;

    @NonNull
    private final String name;

    @Nullable
    private final String url;

    public SetCompanyOptions(@NonNull PluginCall call) throws Exception {
        String name = call.getString("name");
        if (name == null) {
            throw CustomExceptions.NAME_MISSING;
        }
        this.name = name;
        this.description = call.getString("description");
        this.url = call.getString("url");
        JSObject employment = call.getObject("employment");
        this.employmentRole = employment == null ? null : employment.getString("role");
        this.employmentTitle = employment == null ? null : employment.getString("title");
        JSObject geolocation = call.getObject("geolocation");
        this.geolocationCity = geolocation == null ? null : geolocation.getString("city");
        this.geolocationCountry = geolocation == null ? null : geolocation.getString("country");
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Nullable
    public String getEmploymentRole() {
        return employmentRole;
    }

    @Nullable
    public String getEmploymentTitle() {
        return employmentTitle;
    }

    @Nullable
    public String getGeolocationCity() {
        return geolocationCity;
    }

    @Nullable
    public String getGeolocationCountry() {
        return geolocationCountry;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Nullable
    public String getUrl() {
        return url;
    }
}
