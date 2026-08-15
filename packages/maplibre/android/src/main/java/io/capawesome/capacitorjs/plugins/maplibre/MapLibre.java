package io.capawesome.capacitorjs.plugins.maplibre;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.capawesome.capacitorjs.plugins.maplibre.classes.Camera;
import io.capawesome.capacitorjs.plugins.maplibre.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.GestureSettings;
import io.capawesome.capacitorjs.plugins.maplibre.classes.LayerPaint;
import io.capawesome.capacitorjs.plugins.maplibre.classes.MapFrame;
import io.capawesome.capacitorjs.plugins.maplibre.classes.MapInstance;
import io.capawesome.capacitorjs.plugins.maplibre.classes.Marker;
import io.capawesome.capacitorjs.plugins.maplibre.classes.MarkerIcon;
import io.capawesome.capacitorjs.plugins.maplibre.classes.MarkerIconLoader;
import io.capawesome.capacitorjs.plugins.maplibre.classes.Padding;
import io.capawesome.capacitorjs.plugins.maplibre.classes.Polyline;
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
import io.capawesome.capacitorjs.plugins.maplibre.interfaces.NonEmptyCallback;
import io.capawesome.capacitorjs.plugins.maplibre.interfaces.NonEmptyResultCallback;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.maplibre.android.camera.CameraPosition;
import org.maplibre.android.camera.CameraUpdate;
import org.maplibre.android.camera.CameraUpdateFactory;
import org.maplibre.android.geometry.LatLng;
import org.maplibre.android.location.CompassEngine;
import org.maplibre.android.location.CompassListener;
import org.maplibre.android.location.LocationComponent;
import org.maplibre.android.location.LocationComponentActivationOptions;
import org.maplibre.android.location.engine.LocationEngine;
import org.maplibre.android.location.engine.LocationEngineCallback;
import org.maplibre.android.location.engine.LocationEngineRequest;
import org.maplibre.android.location.engine.LocationEngineResult;
import org.maplibre.android.location.modes.CameraMode;
import org.maplibre.android.location.modes.RenderMode;
import org.maplibre.android.maps.MapLibreMap;
import org.maplibre.android.maps.MapLibreMapOptions;
import org.maplibre.android.maps.MapView;
import org.maplibre.android.maps.Style;
import org.maplibre.android.maps.UiSettings;
import org.maplibre.android.plugins.annotation.Line;
import org.maplibre.android.plugins.annotation.LineManager;
import org.maplibre.android.plugins.annotation.LineOptions;
import org.maplibre.android.plugins.annotation.OnSymbolDragListener;
import org.maplibre.android.plugins.annotation.Symbol;
import org.maplibre.android.plugins.annotation.SymbolManager;
import org.maplibre.android.plugins.annotation.SymbolOptions;
import org.maplibre.android.style.layers.CircleLayer;
import org.maplibre.android.style.layers.FillLayer;
import org.maplibre.android.style.layers.Layer;
import org.maplibre.android.style.layers.LineLayer;
import org.maplibre.android.style.layers.Property;
import org.maplibre.android.style.layers.PropertyFactory;
import org.maplibre.android.style.layers.PropertyValue;
import org.maplibre.android.style.sources.GeoJsonSource;
import org.maplibre.android.utils.ColorUtils;

public class MapLibre {

    private static final long LOCATION_INTERVAL_IN_MILLISECONDS = 1000;

    @NonNull
    private final MarkerIconLoader iconLoader;

    @NonNull
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @NonNull
    private final HashMap<String, MapInstance> maps = new HashMap<>();

    @NonNull
    private final MapLibrePlugin plugin;

    @NonNull
    private final List<MotionEvent> touchEvents = new ArrayList<>();

    @Nullable
    private String touchMapId;

    @Nullable
    private String touchRequestId;

    public MapLibre(@NonNull MapLibrePlugin plugin) {
        this.plugin = plugin;
        this.iconLoader = new MarkerIconLoader(plugin.getContext());
        registerTouchListener();
    }

    public void addGeoJsonSource(@NonNull AddGeoJsonSourceOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                Style style = getStyle(getMapInstance(options.getMapId()));
                JSObject data = options.getData();
                String url = options.getUrl();
                if (data != null) {
                    style.addSource(new GeoJsonSource(options.getSourceId(), data.toString()));
                } else if (url != null) {
                    style.addSource(new GeoJsonSource(options.getSourceId(), URI.create(url)));
                }
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void addLayer(@NonNull AddLayerOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                Style style = getStyle(getMapInstance(options.getMapId()));
                if (style.getSource(options.getSourceId()) == null) {
                    throw CustomExceptions.SOURCE_NOT_FOUND;
                }
                Layer layer = createLayer(options);
                if (options.getBelowLayerId() == null) {
                    style.addLayer(layer);
                } else {
                    style.addLayerBelow(layer, options.getBelowLayerId());
                }
                if (options.getMaxZoom() != null) {
                    layer.setMaxZoom(options.getMaxZoom().floatValue());
                }
                if (options.getMinZoom() != null) {
                    layer.setMinZoom(options.getMinZoom().floatValue());
                }
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void addMarker(@NonNull AddMarkerOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                addMarkers(getMapInstance(options.getMapId()), Collections.singletonList(options.getMarker()), callback);
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void addMarkers(@NonNull AddMarkersOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                addMarkers(getMapInstance(options.getMapId()), options.getMarkers(), callback);
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void addPolyline(@NonNull AddPolylineOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                addPolylines(getMapInstance(options.getMapId()), Collections.singletonList(options.getPolyline()));
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void addPolylines(@NonNull AddPolylinesOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                addPolylines(getMapInstance(options.getMapId()), options.getPolylines());
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void createMap(@NonNull CreateMapOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                if (maps.containsKey(options.getMapId())) {
                    throw CustomExceptions.MAP_ALREADY_EXISTS;
                }
                AppCompatActivity activity = plugin.getActivity();
                WebView webView = plugin.getBridge().getWebView();
                ViewGroup parent = webView == null ? null : (ViewGroup) webView.getParent();
                if (activity == null || webView == null || parent == null) {
                    throw CustomExceptions.MAP_CREATE_FAILED;
                }
                org.maplibre.android.MapLibre.getInstance(plugin.getContext());
                MapView mapView = new MapView(activity, createMapViewOptions(options));
                MapInstance instance = new MapInstance(options.getMapId(), mapView, options.getFrame());
                instance.setStyleLoadCallback(createMapCallback(instance, callback));
                mapView.onCreate(null);
                mapView.addOnDidFailLoadingMapListener(message -> instance.notifyStyleLoadResult(CustomExceptions.STYLE_LOAD_FAILED));
                mapView.getMapAsync(map -> handleMapReady(instance, map, options));
                parent.addView(mapView, 0, new ViewGroup.LayoutParams(0, 0));
                mapView.onStart();
                mapView.onResume();
                webView.setBackgroundColor(Color.TRANSPARENT);
                maps.put(options.getMapId(), instance);
                applyFrame(instance);
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void destroy() {
        for (MapInstance instance : new ArrayList<>(maps.values())) {
            destroyMapInstance(instance);
        }
        maps.clear();
        clearTouchRouting();
        restoreWebViewBackground();
    }

    public void destroyMap(@NonNull DestroyMapOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                removeMapInstance(getMapInstance(options.getMapId()));
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void disableUserLocation(@NonNull DisableUserLocationOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                disableUserLocation(getMapInstance(options.getMapId()));
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void elementFromPointResult(@NonNull ElementFromPointResultOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            if (options.getRequestId().equals(touchRequestId)) {
                touchRequestId = null;
                MapInstance instance = options.getMapId() == null ? null : maps.get(options.getMapId());
                if (instance == null) {
                    clearTouchRouting();
                } else {
                    touchMapId = instance.getMapId();
                    for (MotionEvent event : touchEvents) {
                        dispatchTouchEvent(instance, event);
                        event.recycle();
                    }
                    touchEvents.clear();
                }
            }
            callback.success();
        });
    }

    public void enableUserLocation(@NonNull EnableUserLocationOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                MapInstance instance = getMapInstance(options.getMapId());
                enableUserLocation(instance, MapLibreHelper.toCameraMode(options.getTrackingMode()));
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void fitBounds(@NonNull FitBoundsOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                MapLibreMap map = getMap(getMapInstance(options.getMapId()));
                Padding padding = options.getPadding();
                int[] paddingInDevicePixels = {
                    convertToDevicePixels(padding.getLeft()),
                    convertToDevicePixels(padding.getTop()),
                    convertToDevicePixels(padding.getRight()),
                    convertToDevicePixels(padding.getBottom())
                };
                CameraPosition position = map.getCameraForLatLngBounds(options.getBounds(), paddingInDevicePixels);
                if (position == null) {
                    throw CustomExceptions.MAP_NOT_READY;
                }
                if (options.getMaxZoom() != null && position.zoom > options.getMaxZoom()) {
                    position = new CameraPosition.Builder(position).zoom(options.getMaxZoom()).build();
                }
                moveCamera(map, CameraUpdateFactory.newCameraPosition(position), options.getAnimate(), options.getAnimationDuration());
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void getCamera(@NonNull GetCameraOptions options, @NonNull NonEmptyResultCallback<GetCameraResult> callback) {
        runOnMainThread(() -> {
            try {
                MapLibreMap map = getMap(getMapInstance(options.getMapId()));
                callback.success(new GetCameraResult(new Camera(map.getCameraPosition())));
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void handleOnPause() {
        for (MapInstance instance : maps.values()) {
            instance.getMapView().onPause();
        }
    }

    public void handleOnResume() {
        for (MapInstance instance : maps.values()) {
            instance.getMapView().onResume();
        }
    }

    public void handleOnStart() {
        for (MapInstance instance : maps.values()) {
            instance.getMapView().onStart();
        }
    }

    public void handleOnStop() {
        for (MapInstance instance : maps.values()) {
            instance.getMapView().onStop();
        }
    }

    public void removeAllMarkers(@NonNull RemoveAllMarkersOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                MapInstance instance = getMapInstance(options.getMapId());
                getSymbolManager(instance).deleteAll();
                instance.removeMarkers();
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void removeAllPolylines(@NonNull RemoveAllPolylinesOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                MapInstance instance = getMapInstance(options.getMapId());
                getLineManager(instance).deleteAll();
                instance.removePolylines();
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void removeGeoJsonSourceById(@NonNull RemoveGeoJsonSourceByIdOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                Style style = getStyle(getMapInstance(options.getMapId()));
                if (style.getSource(options.getSourceId()) == null) {
                    throw CustomExceptions.SOURCE_NOT_FOUND;
                }
                style.removeSource(options.getSourceId());
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void removeLayerById(@NonNull RemoveLayerByIdOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                Style style = getStyle(getMapInstance(options.getMapId()));
                if (style.getLayer(options.getLayerId()) == null) {
                    throw CustomExceptions.LAYER_NOT_FOUND;
                }
                style.removeLayer(options.getLayerId());
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void removeMarkerById(@NonNull RemoveMarkerByIdOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                removeMarkers(getMapInstance(options.getMapId()), Collections.singletonList(options.getMarkerId()));
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void removeMarkersByIds(@NonNull RemoveMarkersByIdsOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                removeMarkers(getMapInstance(options.getMapId()), options.getMarkerIds());
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void removePolylineById(@NonNull RemovePolylineByIdOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                removePolylines(getMapInstance(options.getMapId()), Collections.singletonList(options.getPolylineId()));
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void removePolylinesByIds(@NonNull RemovePolylinesByIdsOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                removePolylines(getMapInstance(options.getMapId()), options.getPolylineIds());
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void setCamera(@NonNull SetCameraOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                MapLibreMap map = getMap(getMapInstance(options.getMapId()));
                CameraPosition.Builder builder = new CameraPosition.Builder(map.getCameraPosition());
                if (options.getBearing() != null) {
                    builder.bearing(options.getBearing());
                }
                if (options.getCenter() != null) {
                    builder.target(options.getCenter());
                }
                if (options.getPadding() != null) {
                    Padding padding = options.getPadding();
                    builder.padding(
                        convertToDevicePixels(padding.getLeft()),
                        convertToDevicePixels(padding.getTop()),
                        convertToDevicePixels(padding.getRight()),
                        convertToDevicePixels(padding.getBottom())
                    );
                }
                if (options.getPitch() != null) {
                    builder.tilt(options.getPitch());
                }
                if (options.getZoom() != null) {
                    builder.zoom(options.getZoom());
                }
                CameraUpdate update = CameraUpdateFactory.newCameraPosition(builder.build());
                moveCamera(map, update, options.getAnimate(), options.getAnimationDuration());
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void setFrame(@NonNull SetFrameOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                MapInstance instance = getMapInstance(options.getMapId());
                instance.setFrame(options.getFrame());
                applyFrame(instance);
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void setGesturesEnabled(@NonNull SetGesturesEnabledOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                MapLibreMap map = getMap(getMapInstance(options.getMapId()));
                applyGestureSettings(map.getUiSettings(), options.getGestures());
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void setStyle(@NonNull SetStyleOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                MapInstance instance = getMapInstance(options.getMapId());
                MapLibreMap map = getMap(instance);
                destroyAnnotationManagers(instance);
                instance.removeStyleResources();
                instance.setStyleLoadCallback(callback);
                map.setStyle(createStyleBuilder(options.getJson(), options.getUrl()), style -> handleStyleLoaded(instance, style));
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void updateGeoJsonSourceById(@NonNull UpdateGeoJsonSourceByIdOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                Style style = getStyle(getMapInstance(options.getMapId()));
                GeoJsonSource source = style.getSourceAs(options.getSourceId());
                if (source == null) {
                    throw CustomExceptions.SOURCE_NOT_FOUND;
                }
                JSObject data = options.getData();
                String url = options.getUrl();
                if (data != null) {
                    source.setGeoJson(data.toString());
                } else if (url != null) {
                    source.setUri(URI.create(url));
                }
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void updateMarkerById(@NonNull UpdateMarkerByIdOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                MapInstance instance = getMapInstance(options.getMapId());
                Symbol marker = instance.getMarker(options.getMarkerId());
                MarkerIcon currentIcon = instance.getMarkerIcon(options.getMarkerId());
                if (marker == null || currentIcon == null) {
                    throw CustomExceptions.MARKER_NOT_FOUND;
                }
                MarkerIcon icon = currentIcon.apply(options.getIcon());
                if (icon.getKey().equals(currentIcon.getKey())) {
                    updateMarker(instance, marker, options);
                    callback.success();
                    return;
                }
                Style style = getStyle(instance);
                iconLoader.loadIcons(
                    Collections.singletonList(icon),
                    new NonEmptyCallback<Map<String, Bitmap>>() {
                        @Override
                        public void success(@NonNull Map<String, Bitmap> bitmaps) {
                            try {
                                registerIcon(instance, style, icon, bitmaps);
                                marker.setIconImage(icon.getKey());
                                instance.putMarker(options.getMarkerId(), marker, icon);
                                updateMarker(instance, marker, options);
                                callback.success();
                            } catch (Exception exception) {
                                callback.error(exception);
                            }
                        }

                        @Override
                        public void error(@NonNull Exception exception) {
                            callback.error(exception);
                        }
                    }
                );
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void updatePolylineById(@NonNull UpdatePolylineByIdOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                MapInstance instance = getMapInstance(options.getMapId());
                Line polyline = instance.getPolyline(options.getPolylineId());
                if (polyline == null) {
                    throw CustomExceptions.POLYLINE_NOT_FOUND;
                }
                if (options.getColor() != null) {
                    polyline.setLineColor(options.getColor());
                }
                if (options.getCoordinates() != null) {
                    polyline.setLatLngs(options.getCoordinates());
                }
                if (options.getOpacity() != null) {
                    polyline.setLineOpacity(options.getOpacity().floatValue());
                }
                if (options.getWidth() != null) {
                    polyline.setLineWidth(options.getWidth().floatValue());
                }
                getLineManager(instance).update(polyline);
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    private void addMarkers(@NonNull MapInstance instance, @NonNull List<Marker> markers, @NonNull EmptyCallback callback)
        throws Exception {
        SymbolManager symbolManager = getSymbolManager(instance);
        Style style = getStyle(instance);
        List<MarkerIcon> icons = new ArrayList<>();
        for (Marker marker : markers) {
            icons.add(marker.getIcon());
        }
        iconLoader.loadIcons(
            icons,
            new NonEmptyCallback<Map<String, Bitmap>>() {
                @Override
                public void success(@NonNull Map<String, Bitmap> bitmaps) {
                    try {
                        for (Marker marker : markers) {
                            registerIcon(instance, style, marker.getIcon(), bitmaps);
                            removeMarkerIfExists(instance, symbolManager, marker.getId());
                            Symbol symbol = symbolManager.create(createSymbolOptions(marker));
                            instance.putMarker(marker.getId(), symbol, marker.getIcon());
                        }
                        callback.success();
                    } catch (Exception exception) {
                        callback.error(exception);
                    }
                }

                @Override
                public void error(@NonNull Exception exception) {
                    callback.error(exception);
                }
            }
        );
    }

    private void addPolylines(@NonNull MapInstance instance, @NonNull List<Polyline> polylines) throws Exception {
        LineManager lineManager = getLineManager(instance);
        for (Polyline polyline : polylines) {
            removePolylineIfExists(instance, lineManager, polyline.getId());
            Line line = lineManager.create(createLineOptions(polyline));
            instance.putPolyline(polyline.getId(), line);
        }
    }

    private void applyFrame(@NonNull MapInstance instance) {
        MapFrame frame = instance.getFrame();
        MapView mapView = instance.getMapView();
        WebView webView = plugin.getBridge().getWebView();
        int height = convertToDevicePixels(frame.getHeight());
        int width = convertToDevicePixels(frame.getWidth());
        ViewGroup.LayoutParams layoutParams = mapView.getLayoutParams();
        layoutParams.height = height;
        layoutParams.width = width;
        mapView.setLayoutParams(layoutParams);
        mapView.setX((webView == null ? 0 : webView.getLeft()) + convertToDevicePixels(frame.getX()));
        mapView.setY((webView == null ? 0 : webView.getTop()) + convertToDevicePixels(frame.getY()));
        mapView.setVisibility(height <= 0 || width <= 0 ? View.GONE : View.VISIBLE);
    }

    private void applyGestureSettings(@NonNull UiSettings settings, @NonNull GestureSettings gestures) {
        if (gestures.getPan() != null) {
            settings.setScrollGesturesEnabled(gestures.getPan());
        }
        if (gestures.getRotate() != null) {
            settings.setRotateGesturesEnabled(gestures.getRotate());
        }
        if (gestures.getTilt() != null) {
            settings.setTiltGesturesEnabled(gestures.getTilt());
        }
        if (gestures.getZoom() != null) {
            settings.setDoubleTapGesturesEnabled(gestures.getZoom());
            settings.setQuickZoomGesturesEnabled(gestures.getZoom());
            settings.setZoomGesturesEnabled(gestures.getZoom());
        }
    }

    private void clearTouchRouting() {
        for (MotionEvent event : touchEvents) {
            event.recycle();
        }
        touchEvents.clear();
        touchMapId = null;
        touchRequestId = null;
    }

    private int convertToDevicePixels(double cssPixels) {
        return Math.round((float) cssPixels * getDensity());
    }

    @NonNull
    private Layer createLayer(@NonNull AddLayerOptions options) {
        LayerPaint paint = options.getPaint();
        List<PropertyValue<?>> properties = new ArrayList<>();
        switch (options.getType()) {
            case "circle":
                if (paint.getCircleColor() != null) {
                    properties.add(PropertyFactory.circleColor(paint.getCircleColor()));
                }
                if (paint.getCircleOpacity() != null) {
                    properties.add(PropertyFactory.circleOpacity(paint.getCircleOpacity().floatValue()));
                }
                if (paint.getCircleRadius() != null) {
                    properties.add(PropertyFactory.circleRadius(paint.getCircleRadius().floatValue()));
                }
                if (paint.getCircleStrokeColor() != null) {
                    properties.add(PropertyFactory.circleStrokeColor(paint.getCircleStrokeColor()));
                }
                if (paint.getCircleStrokeWidth() != null) {
                    properties.add(PropertyFactory.circleStrokeWidth(paint.getCircleStrokeWidth().floatValue()));
                }
                return new CircleLayer(options.getLayerId(), options.getSourceId()).withProperties(
                    properties.toArray(new PropertyValue<?>[0])
                );
            case "fill":
                if (paint.getFillColor() != null) {
                    properties.add(PropertyFactory.fillColor(paint.getFillColor()));
                }
                if (paint.getFillOpacity() != null) {
                    properties.add(PropertyFactory.fillOpacity(paint.getFillOpacity().floatValue()));
                }
                if (paint.getFillOutlineColor() != null) {
                    properties.add(PropertyFactory.fillOutlineColor(paint.getFillOutlineColor()));
                }
                return new FillLayer(options.getLayerId(), options.getSourceId()).withProperties(
                    properties.toArray(new PropertyValue<?>[0])
                );
            default:
                if (paint.getLineColor() != null) {
                    properties.add(PropertyFactory.lineColor(paint.getLineColor()));
                }
                if (paint.getLineOpacity() != null) {
                    properties.add(PropertyFactory.lineOpacity(paint.getLineOpacity().floatValue()));
                }
                if (paint.getLineWidth() != null) {
                    properties.add(PropertyFactory.lineWidth(paint.getLineWidth().floatValue()));
                }
                return new LineLayer(options.getLayerId(), options.getSourceId()).withProperties(
                    properties.toArray(new PropertyValue<?>[0])
                );
        }
    }

    @NonNull
    private LineOptions createLineOptions(@NonNull Polyline polyline) {
        return new LineOptions()
            .withLatLngs(polyline.getCoordinates())
            .withLineColor(ColorUtils.colorToRgbaString(polyline.getColor()))
            .withLineOpacity((float) polyline.getOpacity())
            .withLineWidth((float) polyline.getWidth());
    }

    @NonNull
    private EmptyCallback createMapCallback(@NonNull MapInstance instance, @NonNull EmptyCallback callback) {
        return new EmptyCallback() {
            @Override
            public void success() {
                callback.success();
            }

            @Override
            public void error(@NonNull Exception exception) {
                removeMapInstance(instance);
                callback.error(exception);
            }
        };
    }

    @NonNull
    private MapLibreMapOptions createMapViewOptions(@NonNull CreateMapOptions options) {
        CameraPosition.Builder builder = new CameraPosition.Builder()
            .bearing(options.getBearing())
            .tilt(options.getPitch())
            .zoom(options.getZoom());
        if (options.getCenter() != null) {
            builder.target(options.getCenter());
        }
        MapLibreMapOptions mapViewOptions = MapLibreMapOptions.createFromAttributes(plugin.getContext()).camera(builder.build());
        if (options.getMaxZoom() != null) {
            mapViewOptions.maxZoomPreference(options.getMaxZoom());
        }
        if (options.getMinZoom() != null) {
            mapViewOptions.minZoomPreference(options.getMinZoom());
        }
        return mapViewOptions;
    }

    @NonNull
    private Style.Builder createStyleBuilder(@Nullable String styleJson, @Nullable String styleUrl) throws Exception {
        Style.Builder builder = new Style.Builder();
        if (styleJson != null) {
            return builder.fromJson(styleJson);
        }
        if (styleUrl != null) {
            return builder.fromUri(styleUrl);
        }
        throw CustomExceptions.JSON_OR_URL_MISSING;
    }

    @NonNull
    private OnSymbolDragListener createSymbolDragListener(@NonNull MapInstance instance) {
        return new OnSymbolDragListener() {
            @Override
            public void onAnnotationDragStarted(Symbol marker) {
                String markerId = getMarkerId(marker);
                if (markerId != null) {
                    plugin.notifyMarkerDragStartListeners(new MarkerDragStartEvent(marker.getLatLng(), instance.getMapId(), markerId));
                }
            }

            @Override
            public void onAnnotationDrag(Symbol marker) {
                String markerId = getMarkerId(marker);
                if (markerId != null) {
                    plugin.notifyMarkerDragListeners(new MarkerDragEvent(marker.getLatLng(), instance.getMapId(), markerId));
                }
            }

            @Override
            public void onAnnotationDragFinished(Symbol marker) {
                String markerId = getMarkerId(marker);
                if (markerId != null) {
                    plugin.notifyMarkerDragEndListeners(new MarkerDragEndEvent(marker.getLatLng(), instance.getMapId(), markerId));
                }
            }
        };
    }

    @NonNull
    private SymbolOptions createSymbolOptions(@NonNull Marker marker) {
        return new SymbolOptions()
            .withData(new JsonPrimitive(marker.getId()))
            .withDraggable(marker.getDraggable())
            .withIconAnchor(MapLibreHelper.toIconAnchor(marker.getIconAnchor()))
            .withIconImage(marker.getIcon().getKey())
            .withIconOpacity((float) marker.getOpacity())
            .withIconRotate((float) marker.getRotation())
            .withLatLng(marker.getCoordinates());
    }

    private void destroyAnnotationManagers(@NonNull MapInstance instance) {
        LineManager lineManager = instance.getLineManager();
        SymbolManager symbolManager = instance.getSymbolManager();
        if (lineManager != null) {
            lineManager.onDestroy();
            instance.setLineManager(null);
        }
        if (symbolManager != null) {
            symbolManager.onDestroy();
            instance.setSymbolManager(null);
        }
    }

    private void destroyMapInstance(@NonNull MapInstance instance) {
        disableUserLocation(instance);
        destroyAnnotationManagers(instance);
        instance.removeStyleResources();
        MapView mapView = instance.getMapView();
        mapView.onPause();
        mapView.onStop();
        mapView.onDestroy();
        ViewGroup parent = (ViewGroup) mapView.getParent();
        if (parent != null) {
            parent.removeView(mapView);
        }
    }

    @SuppressLint("MissingPermission")
    private void disableUserLocation(@NonNull MapInstance instance) {
        MapLibreMap map = instance.getMap();
        if (map == null || !instance.isUserLocationEnabled()) {
            return;
        }
        LocationComponent component = map.getLocationComponent();
        unregisterLocationListeners(instance, component);
        component.setLocationComponentEnabled(false);
        instance.setUserLocationEnabled(false);
    }

    private void dispatchTouchEvent(@NonNull MapInstance instance, @NonNull MotionEvent event) {
        MapFrame frame = instance.getFrame();
        MotionEvent localEvent = MotionEvent.obtain(event);
        localEvent.offsetLocation(-convertToDevicePixels(frame.getX()), -convertToDevicePixels(frame.getY()));
        instance.getMapView().dispatchTouchEvent(localEvent);
        localEvent.recycle();
    }

    @SuppressLint("MissingPermission")
    private void enableUserLocation(@NonNull MapInstance instance, int cameraMode) throws Exception {
        LocationComponent component = getMap(instance).getLocationComponent();
        component.activateLocationComponent(LocationComponentActivationOptions.builder(plugin.getContext(), getStyle(instance)).build());
        component.setLocationComponentEnabled(true);
        component.setCameraMode(cameraMode);
        component.setRenderMode(cameraMode == CameraMode.TRACKING_COMPASS ? RenderMode.COMPASS : RenderMode.NORMAL);
        registerLocationListeners(instance, component);
        instance.setUserLocationEnabled(true);
    }

    @Nullable
    private MapInstance findMapInstanceAt(float x, float y) {
        for (MapInstance instance : maps.values()) {
            MapFrame frame = instance.getFrame();
            if (frame.getHeight() <= 0 || frame.getWidth() <= 0) {
                continue;
            }
            float left = convertToDevicePixels(frame.getX());
            float top = convertToDevicePixels(frame.getY());
            if (
                x >= left &&
                x < left + convertToDevicePixels(frame.getWidth()) &&
                y >= top &&
                y < top + convertToDevicePixels(frame.getHeight())
            ) {
                return instance;
            }
        }
        return null;
    }

    private float getDensity() {
        return plugin.getContext().getResources().getDisplayMetrics().density;
    }

    @NonNull
    private LineManager getLineManager(@NonNull MapInstance instance) throws Exception {
        LineManager lineManager = instance.getLineManager();
        if (lineManager == null) {
            throw CustomExceptions.MAP_NOT_READY;
        }
        return lineManager;
    }

    @NonNull
    private MapLibreMap getMap(@NonNull MapInstance instance) throws Exception {
        MapLibreMap map = instance.getMap();
        if (map == null) {
            throw CustomExceptions.MAP_NOT_READY;
        }
        return map;
    }

    @NonNull
    private MapInstance getMapInstance(@NonNull String mapId) throws Exception {
        MapInstance instance = maps.get(mapId);
        if (instance == null) {
            throw CustomExceptions.MAP_NOT_FOUND;
        }
        return instance;
    }

    @Nullable
    private String getMarkerId(@NonNull Symbol marker) {
        JsonElement data = marker.getData();
        return data == null ? null : data.getAsString();
    }

    @NonNull
    private Style getStyle(@NonNull MapInstance instance) throws Exception {
        Style style = getMap(instance).getStyle();
        if (style == null) {
            throw CustomExceptions.STYLE_NOT_LOADED;
        }
        return style;
    }

    @NonNull
    private SymbolManager getSymbolManager(@NonNull MapInstance instance) throws Exception {
        SymbolManager symbolManager = instance.getSymbolManager();
        if (symbolManager == null) {
            throw CustomExceptions.MAP_NOT_READY;
        }
        return symbolManager;
    }

    private void handleMapReady(@NonNull MapInstance instance, @NonNull MapLibreMap map, @NonNull CreateMapOptions options) {
        try {
            instance.setMap(map);
            applyGestureSettings(map.getUiSettings(), options.getGestures());
            registerMapListeners(instance, map);
            map.setStyle(createStyleBuilder(options.getStyleJson(), options.getStyleUrl()), style -> handleStyleLoaded(instance, style));
        } catch (Exception exception) {
            instance.notifyStyleLoadResult(exception);
        }
    }

    private void handleStyleLoaded(@NonNull MapInstance instance, @NonNull Style style) {
        try {
            MapLibreMap map = getMap(instance);
            MapView mapView = instance.getMapView();
            instance.setLineManager(new LineManager(mapView, map, style));
            SymbolManager symbolManager = new SymbolManager(mapView, map, style);
            symbolManager.setIconAllowOverlap(true);
            symbolManager.setIconIgnorePlacement(true);
            symbolManager.setIconRotationAlignment(Property.ICON_ROTATION_ALIGNMENT_MAP);
            symbolManager.addClickListener(marker -> {
                notifyMarkerClickListeners(instance, marker);
                return true;
            });
            symbolManager.addDragListener(createSymbolDragListener(instance));
            instance.setSymbolManager(symbolManager);
            if (instance.isUserLocationEnabled()) {
                enableUserLocation(instance, map.getLocationComponent().getCameraMode());
            }
            instance.notifyStyleLoadResult(null);
        } catch (Exception exception) {
            instance.notifyStyleLoadResult(exception);
        }
    }

    private void handleTouchEvent(@NonNull MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            startTouchRouting(event);
        }
        MapInstance instance = touchMapId == null ? null : maps.get(touchMapId);
        if (instance != null) {
            dispatchTouchEvent(instance, event);
        } else if (touchRequestId != null) {
            touchEvents.add(MotionEvent.obtain(event));
        }
    }

    /**
     * Returns whether a marker is rendered at the given point of the map.
     */
    private boolean hasMarkerAt(@NonNull MapInstance instance, @NonNull MapLibreMap map, @NonNull PointF point) {
        SymbolManager symbolManager = instance.getSymbolManager();
        if (symbolManager == null) {
            return false;
        }
        return !map.queryRenderedFeatures(point, symbolManager.getLayerId()).isEmpty();
    }

    @NonNull
    private LatLng interpolateCoordinates(@NonNull LatLng start, @NonNull LatLng end, float fraction) {
        return new LatLng(
            start.getLatitude() + (end.getLatitude() - start.getLatitude()) * fraction,
            start.getLongitude() + (end.getLongitude() - start.getLongitude()) * fraction
        );
    }

    private float interpolateRotation(float start, float end, float fraction) {
        float difference = ((end - start + 540) % 360) - 180;
        return start + difference * fraction;
    }

    private void moveCamera(@NonNull MapLibreMap map, @NonNull CameraUpdate update, boolean animate, int animationDuration) {
        if (animate) {
            map.animateCamera(update, animationDuration);
        } else {
            map.moveCamera(update);
        }
    }

    private void notifyMarkerClickListeners(@NonNull MapInstance instance, @NonNull Symbol marker) {
        String markerId = getMarkerId(marker);
        if (markerId == null) {
            return;
        }
        plugin.notifyMarkerClickListeners(new MarkerClickEvent(marker.getLatLng(), instance.getMapId(), markerId));
    }

    private void registerIcon(
        @NonNull MapInstance instance,
        @NonNull Style style,
        @NonNull MarkerIcon icon,
        @NonNull Map<String, Bitmap> bitmaps
    ) throws Exception {
        if (instance.hasIconKey(icon.getKey())) {
            return;
        }
        Bitmap bitmap = bitmaps.get(icon.getKey());
        if (bitmap == null) {
            throw CustomExceptions.ICON_LOAD_FAILED;
        }
        style.addImage(icon.getKey(), bitmap);
        instance.addIconKey(icon.getKey());
    }

    @SuppressLint("MissingPermission")
    private void registerLocationListeners(@NonNull MapInstance instance, @NonNull LocationComponent component) {
        unregisterLocationListeners(instance, component);
        LocationEngine locationEngine = component.getLocationEngine();
        if (locationEngine != null) {
            LocationEngineCallback<LocationEngineResult> locationCallback = new LocationEngineCallback<>() {
                @Override
                public void onSuccess(LocationEngineResult result) {
                    Location location = result == null ? null : result.getLastLocation();
                    if (location != null) {
                        plugin.notifyUserLocationChangeListeners(
                            new UserLocationChangeEvent(instance.getHeading(), location, instance.getMapId())
                        );
                    }
                }

                @Override
                public void onFailure(@NonNull Exception exception) {
                    Logger.error(MapLibrePlugin.TAG, exception.getMessage(), exception);
                }
            };
            locationEngine.requestLocationUpdates(
                new LocationEngineRequest.Builder(LOCATION_INTERVAL_IN_MILLISECONDS)
                    .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                    .build(),
                locationCallback,
                Looper.getMainLooper()
            );
            instance.setLocationCallback(locationCallback);
        }
        CompassEngine compassEngine = component.getCompassEngine();
        if (compassEngine != null) {
            CompassListener compassListener = new CompassListener() {
                @Override
                public void onCompassChanged(float userHeading) {
                    instance.setHeading(userHeading);
                }

                @Override
                public void onCompassAccuracyChange(int compassStatus) {}
            };
            compassEngine.addCompassListener(compassListener);
            instance.setCompassListener(compassListener);
        }
    }

    private void registerMapListeners(@NonNull MapInstance instance, @NonNull MapLibreMap map) {
        map.addOnCameraIdleListener(() ->
            plugin.notifyCameraIdleListeners(new CameraIdleEvent(new Camera(map.getCameraPosition()), instance.getMapId()))
        );
        map.addOnCameraMoveStartedListener(reason ->
            plugin.notifyCameraMoveStartedListeners(
                new CameraMoveStartedEvent(
                    instance.getMapId(),
                    reason == MapLibreMap.OnCameraMoveStartedListener.REASON_API_GESTURE ? "gesture" : "api"
                )
            )
        );
        map.addOnMapClickListener(coordinates -> {
            PointF point = map.getProjection().toScreenLocation(coordinates);
            if (hasMarkerAt(instance, map, point)) {
                return false;
            }
            float density = getDensity();
            plugin.notifyMapClickListeners(new MapClickEvent(coordinates, instance.getMapId(), point.x / density, point.y / density));
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void registerTouchListener() {
        WebView webView = plugin.getBridge().getWebView();
        if (webView == null) {
            return;
        }
        webView.setOnTouchListener((view, event) -> {
            handleTouchEvent(event);
            return false;
        });
    }

    private void removeMapInstance(@NonNull MapInstance instance) {
        destroyMapInstance(instance);
        maps.remove(instance.getMapId());
        if (maps.isEmpty()) {
            clearTouchRouting();
            restoreWebViewBackground();
        }
    }

    private void removeMarkerIfExists(@NonNull MapInstance instance, @NonNull SymbolManager symbolManager, @NonNull String markerId) {
        Symbol marker = instance.getMarker(markerId);
        if (marker == null) {
            return;
        }
        symbolManager.delete(marker);
        instance.removeMarker(markerId);
    }

    /**
     * Removes the given markers from the map. No marker is removed if a marker does not exist.
     */
    private void removeMarkers(@NonNull MapInstance instance, @NonNull List<String> markerIds) throws Exception {
        SymbolManager symbolManager = getSymbolManager(instance);
        Map<String, Symbol> markers = new LinkedHashMap<>();
        for (String markerId : markerIds) {
            Symbol marker = instance.getMarker(markerId);
            if (marker == null) {
                throw CustomExceptions.MARKER_NOT_FOUND;
            }
            markers.put(markerId, marker);
        }
        for (Map.Entry<String, Symbol> entry : markers.entrySet()) {
            symbolManager.delete(entry.getValue());
            instance.removeMarker(entry.getKey());
        }
    }

    private void removePolylineIfExists(@NonNull MapInstance instance, @NonNull LineManager lineManager, @NonNull String polylineId) {
        Line polyline = instance.getPolyline(polylineId);
        if (polyline == null) {
            return;
        }
        lineManager.delete(polyline);
        instance.removePolyline(polylineId);
    }

    /**
     * Removes the given polylines from the map. No polyline is removed if a polyline does not exist.
     */
    private void removePolylines(@NonNull MapInstance instance, @NonNull List<String> polylineIds) throws Exception {
        LineManager lineManager = getLineManager(instance);
        Map<String, Line> polylines = new LinkedHashMap<>();
        for (String polylineId : polylineIds) {
            Line polyline = instance.getPolyline(polylineId);
            if (polyline == null) {
                throw CustomExceptions.POLYLINE_NOT_FOUND;
            }
            polylines.put(polylineId, polyline);
        }
        for (Map.Entry<String, Line> entry : polylines.entrySet()) {
            lineManager.delete(entry.getValue());
            instance.removePolyline(entry.getKey());
        }
    }

    private void restoreWebViewBackground() {
        WebView webView = plugin.getBridge().getWebView();
        if (webView != null) {
            webView.setBackgroundColor(Color.WHITE);
        }
    }

    private void runOnMainThread(@NonNull Runnable runnable) {
        mainHandler.post(runnable);
    }

    private void startMarkerAnimation(
        @NonNull MapInstance instance,
        @NonNull SymbolManager symbolManager,
        @NonNull Symbol marker,
        @NonNull UpdateMarkerByIdOptions options
    ) {
        LatLng startCoordinates = marker.getLatLng();
        Float startRotation = marker.getIconRotate();
        LatLng endCoordinates = options.getCoordinates();
        Double endRotation = options.getRotation();
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(options.getAnimationDuration());
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> {
            float fraction = (float) animation.getAnimatedValue();
            if (endCoordinates != null) {
                marker.setLatLng(interpolateCoordinates(startCoordinates, endCoordinates, fraction));
            }
            if (endRotation != null) {
                marker.setIconRotate(interpolateRotation(startRotation == null ? 0 : startRotation, endRotation.floatValue(), fraction));
            }
            symbolManager.update(marker);
        });
        instance.putMarkerAnimator(options.getMarkerId(), animator);
        animator.start();
    }

    private void startTouchRouting(@NonNull MotionEvent event) {
        clearTouchRouting();
        if (findMapInstanceAt(event.getX(), event.getY()) == null) {
            return;
        }
        float density = getDensity();
        String requestId = UUID.randomUUID().toString();
        touchRequestId = requestId;
        plugin.notifyElementFromPointRequestListeners(
            new ElementFromPointRequestEvent(requestId, event.getX() / density, event.getY() / density)
        );
    }

    private void unregisterLocationListeners(@NonNull MapInstance instance, @NonNull LocationComponent component) {
        CompassEngine compassEngine = component.getCompassEngine();
        CompassListener compassListener = instance.getCompassListener();
        LocationEngine locationEngine = component.getLocationEngine();
        LocationEngineCallback<LocationEngineResult> locationCallback = instance.getLocationCallback();
        if (compassEngine != null && compassListener != null) {
            compassEngine.removeCompassListener(compassListener);
        }
        if (locationEngine != null && locationCallback != null) {
            locationEngine.removeLocationUpdates(locationCallback);
        }
        instance.setCompassListener(null);
        instance.setLocationCallback(null);
    }

    private void updateMarker(@NonNull MapInstance instance, @NonNull Symbol marker, @NonNull UpdateMarkerByIdOptions options)
        throws Exception {
        SymbolManager symbolManager = getSymbolManager(instance);
        instance.cancelMarkerAnimation(options.getMarkerId());
        if (options.getDraggable() != null) {
            marker.setDraggable(options.getDraggable());
        }
        if (options.getIconAnchor() != null) {
            marker.setIconAnchor(MapLibreHelper.toIconAnchor(options.getIconAnchor()));
        }
        if (options.getOpacity() != null) {
            marker.setIconOpacity(options.getOpacity().floatValue());
        }
        if (options.getAnimate() && (options.getCoordinates() != null || options.getRotation() != null)) {
            startMarkerAnimation(instance, symbolManager, marker, options);
            return;
        }
        if (options.getCoordinates() != null) {
            marker.setLatLng(options.getCoordinates());
        }
        if (options.getRotation() != null) {
            marker.setIconRotate(options.getRotation().floatValue());
        }
        symbolManager.update(marker);
    }
}
