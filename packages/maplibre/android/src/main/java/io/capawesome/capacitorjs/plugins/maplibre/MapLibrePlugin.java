package io.capawesome.capacitorjs.plugins.maplibre;

import android.Manifest;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import io.capawesome.capacitorjs.plugins.maplibre.classes.CustomException;
import io.capawesome.capacitorjs.plugins.maplibre.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.events.CameraIdleEvent;
import io.capawesome.capacitorjs.plugins.maplibre.classes.events.CameraMoveStartedEvent;
import io.capawesome.capacitorjs.plugins.maplibre.classes.events.ElementFromPointRequestEvent;
import io.capawesome.capacitorjs.plugins.maplibre.classes.events.MapClickEvent;
import io.capawesome.capacitorjs.plugins.maplibre.classes.events.MarkerClickEvent;
import io.capawesome.capacitorjs.plugins.maplibre.classes.events.MarkerDragEndEvent;
import io.capawesome.capacitorjs.plugins.maplibre.classes.events.MarkerDragEvent;
import io.capawesome.capacitorjs.plugins.maplibre.classes.events.MarkerDragStartEvent;
import io.capawesome.capacitorjs.plugins.maplibre.classes.events.UserLocationChangeEvent;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.AddGeoJsonSourceOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.AddLayerOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.AddMarkerOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.AddMarkersOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.AddPolylineOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.AddPolylinesOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.CreateMapOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.DestroyMapOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.DisableUserLocationOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.ElementFromPointResultOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.EnableUserLocationOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.FitBoundsOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.GetCameraOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.RemoveAllMarkersOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.RemoveAllPolylinesOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.RemoveGeoJsonSourceByIdOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.RemoveLayerByIdOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.RemoveMarkerByIdOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.RemoveMarkersByIdsOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.RemovePolylineByIdOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.RemovePolylinesByIdsOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.SetCameraOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.SetFrameOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.SetGesturesEnabledOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.SetStyleOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.UpdateGeoJsonSourceByIdOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.UpdateMarkerByIdOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.options.UpdatePolylineByIdOptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.results.GetCameraResult;
import io.capawesome.capacitorjs.plugins.maplibre.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.maplibre.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.maplibre.interfaces.Result;

@CapacitorPlugin(
    name = "MapLibre",
    permissions = {
        @Permission(
            strings = { Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION },
            alias = MapLibrePlugin.PERMISSION_LOCATION
        )
    }
)
public class MapLibrePlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String EVENT_CAMERA_IDLE = "cameraIdle";
    public static final String EVENT_CAMERA_MOVE_STARTED = "cameraMoveStarted";
    public static final String EVENT_ELEMENT_FROM_POINT_REQUEST = "elementFromPointRequest";
    public static final String EVENT_MAP_CLICK = "mapClick";
    public static final String EVENT_MARKER_CLICK = "markerClick";
    public static final String EVENT_MARKER_DRAG = "markerDrag";
    public static final String EVENT_MARKER_DRAG_END = "markerDragEnd";
    public static final String EVENT_MARKER_DRAG_START = "markerDragStart";
    public static final String EVENT_USER_LOCATION_CHANGE = "userLocationChange";
    public static final String PERMISSION_LOCATION = "location";
    public static final String TAG = "MapLibrePlugin";

    private MapLibre implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new MapLibre(this);
    }

    @PluginMethod
    public void addGeoJsonSource(PluginCall call) {
        try {
            AddGeoJsonSourceOptions options = new AddGeoJsonSourceOptions(call);
            implementation.addGeoJsonSource(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void addLayer(PluginCall call) {
        try {
            AddLayerOptions options = new AddLayerOptions(call);
            implementation.addLayer(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void addMarker(PluginCall call) {
        try {
            AddMarkerOptions options = new AddMarkerOptions(call);
            implementation.addMarker(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void addMarkers(PluginCall call) {
        try {
            AddMarkersOptions options = new AddMarkersOptions(call);
            implementation.addMarkers(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void addPolyline(PluginCall call) {
        try {
            AddPolylineOptions options = new AddPolylineOptions(call);
            implementation.addPolyline(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void addPolylines(PluginCall call) {
        try {
            AddPolylinesOptions options = new AddPolylinesOptions(call);
            implementation.addPolylines(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void createMap(PluginCall call) {
        try {
            CreateMapOptions options = new CreateMapOptions(call);
            implementation.createMap(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void destroyMap(PluginCall call) {
        try {
            DestroyMapOptions options = new DestroyMapOptions(call);
            implementation.destroyMap(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void disableUserLocation(PluginCall call) {
        try {
            DisableUserLocationOptions options = new DisableUserLocationOptions(call);
            implementation.disableUserLocation(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void elementFromPointResult(PluginCall call) {
        try {
            ElementFromPointResultOptions options = new ElementFromPointResultOptions(call);
            implementation.elementFromPointResult(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void enableUserLocation(PluginCall call) {
        try {
            if (getPermissionState(PERMISSION_LOCATION) != PermissionState.GRANTED) {
                requestPermissionForAlias(PERMISSION_LOCATION, call, "handleLocationPermissionCallback");
                return;
            }
            EnableUserLocationOptions options = new EnableUserLocationOptions(call);
            implementation.enableUserLocation(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void fitBounds(PluginCall call) {
        try {
            FitBoundsOptions options = new FitBoundsOptions(call);
            implementation.fitBounds(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getCamera(PluginCall call) {
        try {
            GetCameraOptions options = new GetCameraOptions(call);
            NonEmptyResultCallback<GetCameraResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetCameraResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getCamera(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void notifyCameraIdleListeners(@NonNull CameraIdleEvent event) {
        notifyListeners(EVENT_CAMERA_IDLE, event.toJSObject());
    }

    public void notifyCameraMoveStartedListeners(@NonNull CameraMoveStartedEvent event) {
        notifyListeners(EVENT_CAMERA_MOVE_STARTED, event.toJSObject());
    }

    public void notifyElementFromPointRequestListeners(@NonNull ElementFromPointRequestEvent event) {
        notifyListeners(EVENT_ELEMENT_FROM_POINT_REQUEST, event.toJSObject());
    }

    public void notifyMapClickListeners(@NonNull MapClickEvent event) {
        notifyListeners(EVENT_MAP_CLICK, event.toJSObject());
    }

    public void notifyMarkerClickListeners(@NonNull MarkerClickEvent event) {
        notifyListeners(EVENT_MARKER_CLICK, event.toJSObject());
    }

    public void notifyMarkerDragEndListeners(@NonNull MarkerDragEndEvent event) {
        notifyListeners(EVENT_MARKER_DRAG_END, event.toJSObject());
    }

    public void notifyMarkerDragListeners(@NonNull MarkerDragEvent event) {
        notifyListeners(EVENT_MARKER_DRAG, event.toJSObject());
    }

    public void notifyMarkerDragStartListeners(@NonNull MarkerDragStartEvent event) {
        notifyListeners(EVENT_MARKER_DRAG_START, event.toJSObject());
    }

    public void notifyUserLocationChangeListeners(@NonNull UserLocationChangeEvent event) {
        notifyListeners(EVENT_USER_LOCATION_CHANGE, event.toJSObject());
    }

    @PluginMethod
    public void removeAllMarkers(PluginCall call) {
        try {
            RemoveAllMarkersOptions options = new RemoveAllMarkersOptions(call);
            implementation.removeAllMarkers(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void removeAllPolylines(PluginCall call) {
        try {
            RemoveAllPolylinesOptions options = new RemoveAllPolylinesOptions(call);
            implementation.removeAllPolylines(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void removeGeoJsonSourceById(PluginCall call) {
        try {
            RemoveGeoJsonSourceByIdOptions options = new RemoveGeoJsonSourceByIdOptions(call);
            implementation.removeGeoJsonSourceById(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void removeLayerById(PluginCall call) {
        try {
            RemoveLayerByIdOptions options = new RemoveLayerByIdOptions(call);
            implementation.removeLayerById(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void removeMarkerById(PluginCall call) {
        try {
            RemoveMarkerByIdOptions options = new RemoveMarkerByIdOptions(call);
            implementation.removeMarkerById(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void removeMarkersByIds(PluginCall call) {
        try {
            RemoveMarkersByIdsOptions options = new RemoveMarkersByIdsOptions(call);
            implementation.removeMarkersByIds(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void removePolylineById(PluginCall call) {
        try {
            RemovePolylineByIdOptions options = new RemovePolylineByIdOptions(call);
            implementation.removePolylineById(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void removePolylinesByIds(PluginCall call) {
        try {
            RemovePolylinesByIdsOptions options = new RemovePolylinesByIdsOptions(call);
            implementation.removePolylinesByIds(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setCamera(PluginCall call) {
        try {
            SetCameraOptions options = new SetCameraOptions(call);
            implementation.setCamera(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setFrame(PluginCall call) {
        try {
            SetFrameOptions options = new SetFrameOptions(call);
            implementation.setFrame(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setGesturesEnabled(PluginCall call) {
        try {
            SetGesturesEnabledOptions options = new SetGesturesEnabledOptions(call);
            implementation.setGesturesEnabled(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setStyle(PluginCall call) {
        try {
            SetStyleOptions options = new SetStyleOptions(call);
            implementation.setStyle(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void updateGeoJsonSourceById(PluginCall call) {
        try {
            UpdateGeoJsonSourceByIdOptions options = new UpdateGeoJsonSourceByIdOptions(call);
            implementation.updateGeoJsonSourceById(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void updateMarkerById(PluginCall call) {
        try {
            UpdateMarkerByIdOptions options = new UpdateMarkerByIdOptions(call);
            implementation.updateMarkerById(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void updatePolylineById(PluginCall call) {
        try {
            UpdatePolylineByIdOptions options = new UpdatePolylineByIdOptions(call);
            implementation.updatePolylineById(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @Override
    protected void handleOnDestroy() {
        super.handleOnDestroy();
        if (implementation != null) {
            implementation.destroy();
        }
    }

    @Override
    protected void handleOnPause() {
        super.handleOnPause();
        if (implementation != null) {
            implementation.handleOnPause();
        }
    }

    @Override
    protected void handleOnResume() {
        super.handleOnResume();
        if (implementation != null) {
            implementation.handleOnResume();
        }
    }

    @Override
    protected void handleOnStart() {
        super.handleOnStart();
        if (implementation != null) {
            implementation.handleOnStart();
        }
    }

    @Override
    protected void handleOnStop() {
        super.handleOnStop();
        if (implementation != null) {
            implementation.handleOnStop();
        }
    }

    @NonNull
    private EmptyCallback createEmptyCallback(@NonNull PluginCall call) {
        return new EmptyCallback() {
            @Override
            public void success() {
                resolveCall(call);
            }

            @Override
            public void error(@NonNull Exception exception) {
                rejectCall(call, exception);
            }
        };
    }

    @PermissionCallback
    private void handleLocationPermissionCallback(PluginCall call) {
        if (getPermissionState(PERMISSION_LOCATION) == PermissionState.GRANTED) {
            enableUserLocation(call);
        } else {
            rejectCall(call, CustomExceptions.LOCATION_PERMISSION_DENIED);
        }
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        String code = null;
        if (exception instanceof CustomException) {
            code = ((CustomException) exception).getCode();
        }
        Logger.error(TAG, message, exception);
        call.reject(message, code);
    }

    private void resolveCall(@NonNull PluginCall call) {
        call.resolve();
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
