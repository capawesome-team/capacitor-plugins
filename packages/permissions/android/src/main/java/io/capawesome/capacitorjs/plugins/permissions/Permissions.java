package io.capawesome.capacitorjs.plugins.permissions;

import android.Manifest;
import android.os.Build;
import androidx.annotation.NonNull;
import com.getcapacitor.util.PermissionHelper;
import io.capawesome.capacitorjs.plugins.permissions.classes.CustomException;
import io.capawesome.capacitorjs.plugins.permissions.classes.options.CheckOptions;
import io.capawesome.capacitorjs.plugins.permissions.classes.options.RequestOptions;
import io.capawesome.capacitorjs.plugins.permissions.classes.results.CheckResult;
import io.capawesome.capacitorjs.plugins.permissions.classes.results.PermissionStatus;
import io.capawesome.capacitorjs.plugins.permissions.classes.results.RequestResult;
import io.capawesome.capacitorjs.plugins.permissions.enums.PermissionState;
import io.capawesome.capacitorjs.plugins.permissions.enums.PermissionType;
import io.capawesome.capacitorjs.plugins.permissions.interfaces.NonEmptyResultCallback;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Permissions {

    @NonNull
    private final PermissionsPlugin plugin;

    public Permissions(@NonNull PermissionsPlugin plugin) {
        this.plugin = plugin;
    }

    public void check(@NonNull CheckOptions options, @NonNull NonEmptyResultCallback<CheckResult> callback) {
        List<PermissionStatus> statuses = getPermissionStatuses(options.getPermissions());
        callback.success(new CheckResult(statuses));
    }

    @NonNull
    public String[] getAliasesToRequest(@NonNull List<PermissionType> permissions) {
        Set<String> aliases = new LinkedHashSet<>();
        for (PermissionType permission : permissions) {
            if (getPermissionState(permission) == PermissionState.GRANTED) {
                continue;
            }
            switch (permission) {
                case BLUETOOTH:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        aliases.add(PermissionsPlugin.ALIAS_BLUETOOTH);
                    }
                    break;
                case CALENDAR:
                    aliases.add(PermissionsPlugin.ALIAS_CALENDAR);
                    break;
                case CAMERA:
                    aliases.add(PermissionsPlugin.ALIAS_CAMERA);
                    break;
                case CONTACTS:
                    aliases.add(PermissionsPlugin.ALIAS_CONTACTS);
                    break;
                case LOCATION:
                    aliases.add(PermissionsPlugin.ALIAS_LOCATION_COARSE);
                    aliases.add(PermissionsPlugin.ALIAS_LOCATION_FINE);
                    break;
                case LOCATION_ALWAYS:
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                        aliases.add(PermissionsPlugin.ALIAS_LOCATION_COARSE);
                        aliases.add(PermissionsPlugin.ALIAS_LOCATION_FINE);
                    }
                    break;
                case MICROPHONE:
                    aliases.add(PermissionsPlugin.ALIAS_MICROPHONE);
                    break;
                case MOTION:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        aliases.add(PermissionsPlugin.ALIAS_MOTION);
                    }
                    break;
                case NOTIFICATIONS:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        aliases.add(PermissionsPlugin.ALIAS_NOTIFICATIONS);
                    }
                    break;
                case PHOTOS:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                        aliases.add(PermissionsPlugin.ALIAS_PHOTOS);
                        aliases.add(PermissionsPlugin.ALIAS_PHOTOS_USER_SELECTED);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        aliases.add(PermissionsPlugin.ALIAS_PHOTOS);
                    } else {
                        aliases.add(PermissionsPlugin.ALIAS_PHOTOS_LEGACY);
                    }
                    break;
                default:
                    break;
            }
        }
        return aliases.toArray(new String[0]);
    }

    @NonNull
    public String[] getBackgroundLocationAliasesToRequest(@NonNull List<PermissionType> permissions) {
        if (!permissions.contains(PermissionType.LOCATION_ALWAYS) || Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            return new String[0];
        }
        if (getPermissionState(PermissionType.LOCATION_ALWAYS) == PermissionState.GRANTED) {
            return new String[0];
        }
        return new String[] { PermissionsPlugin.ALIAS_LOCATION_BACKGROUND };
    }

    public void request(@NonNull RequestOptions options, @NonNull NonEmptyResultCallback<RequestResult> callback) {
        List<PermissionStatus> statuses = getPermissionStatuses(options.getPermissions());
        callback.success(new RequestResult(statuses));
    }

    public void validateManifestDeclarations(@NonNull List<PermissionType> permissions) throws CustomException {
        Set<String> requiredPermissionStrings = new LinkedHashSet<>();
        for (PermissionType permission : permissions) {
            requiredPermissionStrings.addAll(Arrays.asList(getManifestStringsForPermission(permission)));
        }
        if (requiredPermissionStrings.isEmpty()) {
            return;
        }
        String[] undefinedPermissionStrings = PermissionHelper.getUndefinedPermissions(
            plugin.getContext(),
            requiredPermissionStrings.toArray(new String[0])
        );
        if (undefinedPermissionStrings.length > 0) {
            throw new CustomException(
                null,
                "The following permissions are not declared in the AndroidManifest.xml file: " +
                String.join(", ", undefinedPermissionStrings) +
                "."
            );
        }
    }

    @NonNull
    private PermissionState getLocationPermissionState() {
        return getMostPermissivePermissionState(
            getPermissionStateForAlias(PermissionsPlugin.ALIAS_LOCATION_FINE),
            getPermissionStateForAlias(PermissionsPlugin.ALIAS_LOCATION_COARSE)
        );
    }

    @NonNull
    private String[] getManifestStringsForPermission(@NonNull PermissionType permission) {
        switch (permission) {
            case BLUETOOTH:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    return new String[] { Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN };
                }
                return new String[0];
            case CALENDAR:
                return new String[] { Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR };
            case CAMERA:
                return new String[] { Manifest.permission.CAMERA };
            case CONTACTS:
                return new String[] { Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS };
            case LOCATION:
                return new String[] { Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION };
            case LOCATION_ALWAYS:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    return new String[] { Manifest.permission.ACCESS_BACKGROUND_LOCATION };
                }
                return new String[] { Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION };
            case MICROPHONE:
                return new String[] { Manifest.permission.RECORD_AUDIO };
            case MOTION:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    return new String[] { Manifest.permission.ACTIVITY_RECOGNITION };
                }
                return new String[0];
            case NOTIFICATIONS:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    return new String[] { Manifest.permission.POST_NOTIFICATIONS };
                }
                return new String[0];
            case PHOTOS:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    return new String[] { Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED };
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    return new String[] { Manifest.permission.READ_MEDIA_IMAGES };
                }
                return new String[] { Manifest.permission.READ_EXTERNAL_STORAGE };
            default:
                return new String[0];
        }
    }

    @NonNull
    private PermissionState getMostPermissivePermissionState(@NonNull PermissionState first, @NonNull PermissionState second) {
        return getRankForPermissionState(first) >= getRankForPermissionState(second) ? first : second;
    }

    @NonNull
    private PermissionState getPermissionState(@NonNull PermissionType permission) {
        switch (permission) {
            case BLUETOOTH:
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                    return PermissionState.GRANTED;
                }
                return getPermissionStateForAlias(PermissionsPlugin.ALIAS_BLUETOOTH);
            case CALENDAR:
                return getPermissionStateForAlias(PermissionsPlugin.ALIAS_CALENDAR);
            case CAMERA:
                return getPermissionStateForAlias(PermissionsPlugin.ALIAS_CAMERA);
            case CONTACTS:
                return getPermissionStateForAlias(PermissionsPlugin.ALIAS_CONTACTS);
            case LOCATION:
                return getLocationPermissionState();
            case LOCATION_ALWAYS:
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    return getLocationPermissionState();
                }
                return getPermissionStateForAlias(PermissionsPlugin.ALIAS_LOCATION_BACKGROUND);
            case MICROPHONE:
                return getPermissionStateForAlias(PermissionsPlugin.ALIAS_MICROPHONE);
            case MOTION:
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    return PermissionState.GRANTED;
                }
                return getPermissionStateForAlias(PermissionsPlugin.ALIAS_MOTION);
            case NOTIFICATIONS:
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    return PermissionState.GRANTED;
                }
                return getPermissionStateForAlias(PermissionsPlugin.ALIAS_NOTIFICATIONS);
            case PHOTOS:
                return getPhotosPermissionState();
            default:
                return PermissionState.UNAVAILABLE;
        }
    }

    @NonNull
    private PermissionState getPermissionStateForAlias(@NonNull String alias) {
        com.getcapacitor.PermissionState state = plugin.getPermissionState(alias);
        if (state == null) {
            return PermissionState.PROMPT;
        }
        switch (state) {
            case GRANTED:
                return PermissionState.GRANTED;
            case DENIED:
                return PermissionState.DENIED;
            case PROMPT_WITH_RATIONALE:
                return PermissionState.PROMPT_WITH_RATIONALE;
            default:
                return PermissionState.PROMPT;
        }
    }

    @NonNull
    private List<PermissionStatus> getPermissionStatuses(@NonNull List<PermissionType> permissions) {
        List<PermissionStatus> statuses = new ArrayList<>();
        for (PermissionType permission : permissions) {
            statuses.add(new PermissionStatus(permission, getPermissionState(permission)));
        }
        return statuses;
    }

    @NonNull
    private PermissionState getPhotosPermissionState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            PermissionState state = getPermissionStateForAlias(PermissionsPlugin.ALIAS_PHOTOS);
            if (state == PermissionState.GRANTED) {
                return PermissionState.GRANTED;
            }
            if (getPermissionStateForAlias(PermissionsPlugin.ALIAS_PHOTOS_USER_SELECTED) == PermissionState.GRANTED) {
                return PermissionState.LIMITED;
            }
            return state;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return getPermissionStateForAlias(PermissionsPlugin.ALIAS_PHOTOS);
        }
        return getPermissionStateForAlias(PermissionsPlugin.ALIAS_PHOTOS_LEGACY);
    }

    private int getRankForPermissionState(@NonNull PermissionState state) {
        switch (state) {
            case GRANTED:
                return 5;
            case LIMITED:
                return 4;
            case PROMPT:
                return 3;
            case PROMPT_WITH_RATIONALE:
                return 2;
            case DENIED:
                return 1;
            default:
                return 0;
        }
    }
}
