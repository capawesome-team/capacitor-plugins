package io.capawesome.capacitorjs.plugins.pixlive;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.vidinoti.android.vdarsdk.VDARAnnotationView;
import com.vidinoti.android.vdarsdk.VDARCode;
import com.vidinoti.android.vdarsdk.VDARContentEventReceiver;
import com.vidinoti.android.vdarsdk.VDARContext;
import com.vidinoti.android.vdarsdk.VDARPrior;
import com.vidinoti.android.vdarsdk.VDARRemoteController;
import com.vidinoti.android.vdarsdk.VDARRemoteControllerListener;
import com.vidinoti.android.vdarsdk.VDARSDKController;
import com.vidinoti.android.vdarsdk.VDARSDKControllerEventReceiver;
import com.vidinoti.android.vdarsdk.VDARTagPrior;
import com.vidinoti.android.vdarsdk.camera.DeviceCameraImageSender;
import com.vidinoti.android.vdarsdk.geopoint.GeoPointManager;
import com.vidinoti.android.vdarsdk.geopoint.VDARGPSPoint;
import io.capawesome.capacitorjs.plugins.pixlive.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.pixlive.classes.options.*;
import io.capawesome.capacitorjs.plugins.pixlive.classes.results.*;
import io.capawesome.capacitorjs.plugins.pixlive.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.pixlive.interfaces.NonEmptyResultCallback;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Pixlive implements VDARSDKControllerEventReceiver, VDARContentEventReceiver, VDARRemoteControllerListener {

    @NonNull
    private final PixlivePlugin plugin;

    @Nullable
    private VDARAnnotationView annotationView;

    @Nullable
    private TouchInterceptorView touchInterceptorView;

    @Nullable
    private VDARContext currentContext;

    public Pixlive(@NonNull PixlivePlugin plugin) {
        this.plugin = plugin;
    }

    public void initialize() {
        Activity activity = plugin.getActivity();
        String licenseKey = plugin.getConfig().getString("licenseKey", "");
        String apiUrl = plugin.getConfig().getString("apiUrl", null);
        String sdkUrl = plugin.getConfig().getString("sdkUrl", null);

        File storageDir = new File(activity.getFilesDir(), "pixliveSDK");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        VDARSDKController.startSDK(activity, storageDir.getAbsolutePath(), licenseKey);
        VDARSDKController controller = VDARSDKController.getInstance();
        if (apiUrl != null) {
            VDARRemoteController.getInstance().setCustomRemoteApiServerEndpoint(apiUrl);
        }
        if (sdkUrl != null) {
            VDARRemoteController.getInstance().setCustomRemoteSdkApiServerEndpoint(sdkUrl);
        }
        controller.setEnableCodesRecognition(true);
        controller.setActivity(activity);
        try {
            controller.setImageSender(new DeviceCameraImageSender());
        } catch (Exception e) {
            // Camera not available
        }
        controller.registerEventReceiver(this);
        controller.registerContentEventReceiver(this);
        VDARRemoteController.getInstance().addProgressListener(this);
    }

    public void handleOnPause() {
        if (annotationView != null && annotationView.getParent() != null && annotationView.getVisibility() == View.VISIBLE) {
            annotationView.onPause();
        }
        VDARSDKController controller = VDARSDKController.getInstance();
        if (controller != null) {
            controller.onActivityPaused(plugin.getActivity());
        }
    }

    public void handleOnResume() {
        if (annotationView != null && annotationView.getParent() != null && annotationView.getVisibility() == View.VISIBLE) {
            annotationView.onResume();
        }
        VDARSDKController controller = VDARSDKController.getInstance();
        if (controller != null) {
            controller.onActivityResumed(plugin.getActivity());
        }
    }

    public void handleOnDestroy() {
        VDARSDKController controller = VDARSDKController.getInstance();
        if (controller != null) {
            controller.unregisterEventReceiver(this);
            controller.unregisterContentEventReceiver(this);
            VDARRemoteController.getInstance().removeProgressListener(this);
        }
    }

    public void synchronize(@NonNull SynchronizeOptions options, @NonNull EmptyCallback callback) {
        try {
            List<VDARPrior> priors = PixliveHelper.buildTagPriors(options.getTags());
            ArrayList<VDARPrior> priorsList = new ArrayList<>(priors);
            VDARRemoteController.getInstance()
                .syncRemoteContextsAsynchronouslyWithPriors(
                    priorsList,
                    new Observer() {
                        @Override
                        public void update(Observable o, Object arg) {
                            VDARRemoteController.ObserverUpdateInfo info = (VDARRemoteController.ObserverUpdateInfo) arg;
                            if (!info.isCompleted()) {
                                return;
                            }
                            if (info.getError() != null) {
                                callback.error(new Exception(info.getError()));
                            } else {
                                callback.success();
                            }
                        }
                    }
                );
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void synchronizeWithToursAndContexts(@NonNull SynchronizeWithToursAndContextsOptions options, @NonNull EmptyCallback callback) {
        try {
            List<VDARPrior> priors = PixliveHelper.buildFullPriors(options.getTags(), options.getTourIds(), options.getContextIds());
            ArrayList<VDARPrior> priorsList = new ArrayList<>(priors);
            VDARRemoteController.getInstance()
                .syncRemoteContextsAsynchronouslyWithPriors(
                    priorsList,
                    new Observer() {
                        @Override
                        public void update(Observable o, Object arg) {
                            VDARRemoteController.ObserverUpdateInfo info = (VDARRemoteController.ObserverUpdateInfo) arg;
                            if (!info.isCompleted()) {
                                return;
                            }
                            if (info.getError() != null) {
                                callback.error(new Exception(info.getError()));
                            } else {
                                callback.success();
                            }
                        }
                    }
                );
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void updateTagMapping(@NonNull UpdateTagMappingOptions options, @NonNull EmptyCallback callback) {
        try {
            List<String> tags = new ArrayList<>();
            for (int i = 0; i < options.getTags().length(); i++) {
                tags.add(options.getTags().getString(i));
            }
            VDARRemoteController.getInstance()
                .syncTagContexts(
                    tags,
                    new VDARRemoteController.Callback<Void>() {
                        @Override
                        public void onSuccess(Void result) {
                            callback.success();
                        }

                        @Override
                        public void onError(String message, Throwable throwable) {
                            callback.error(new Exception(message, throwable));
                        }
                    }
                );
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void enableContextsWithTags(@NonNull EnableContextsWithTagsOptions options, @NonNull EmptyCallback callback) {
        try {
            List<String> tags = new ArrayList<>();
            for (int i = 0; i < options.getTags().length(); i++) {
                tags.add(options.getTags().getString(i));
            }
            VDARSDKController.getInstance().disableContexts();
            VDARSDKController.getInstance().enableContextsWithTags(tags);
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void getContexts(@NonNull NonEmptyResultCallback<GetContextsResult> callback) {
        try {
            ArrayList<String> contextIds = VDARSDKController.getInstance().getAllContextIDs();
            JSArray contextsArray = new JSArray();
            if (contextIds != null) {
                for (String contextId : contextIds) {
                    VDARContext context = VDARSDKController.getInstance().getContext(contextId);
                    if (context != null) {
                        contextsArray.put(PixliveHelper.contextToJSObject(context));
                    }
                }
            }
            callback.success(new GetContextsResult(contextsArray));
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void getContext(@NonNull GetContextOptions options, @NonNull NonEmptyResultCallback<GetContextResult> callback) {
        try {
            VDARContext context = VDARSDKController.getInstance().getContext(options.getContextId());
            if (context == null) {
                callback.error(CustomExceptions.CONTEXT_NOT_FOUND);
                return;
            }
            callback.success(new GetContextResult(PixliveHelper.contextToJSObject(context)));
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void activateContext(@NonNull ActivateContextOptions options, @NonNull EmptyCallback callback) {
        try {
            VDARContext context = VDARSDKController.getInstance().getContext(options.getContextId());
            if (context != null) {
                context.activate();
            }
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void stopContext(@NonNull EmptyCallback callback) {
        try {
            if (currentContext != null) {
                currentContext.stop();
            }
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void getNearbyGPSPoints(
        @NonNull GetNearbyGPSPointsOptions options,
        @NonNull NonEmptyResultCallback<GetNearbyGPSPointsResult> callback
    ) {
        try {
            List<VDARGPSPoint> points = GeoPointManager.getNearbyGPSPoints((float) options.getLatitude(), (float) options.getLongitude());
            JSArray pointsArray = new JSArray();
            if (points != null) {
                for (VDARGPSPoint point : points) {
                    pointsArray.put(PixliveHelper.gpsPointToJSObject(point));
                }
            }
            callback.success(new GetNearbyGPSPointsResult(pointsArray));
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void getGPSPointsInBoundingBox(
        @NonNull GetGPSPointsInBoundingBoxOptions options,
        @NonNull NonEmptyResultCallback<GetGPSPointsInBoundingBoxResult> callback
    ) {
        try {
            List<VDARGPSPoint> points = GeoPointManager.getGPSPointsInBoundingBox(
                (float) options.getMinLatitude(),
                (float) options.getMinLongitude(),
                (float) options.getMaxLatitude(),
                (float) options.getMaxLongitude()
            );
            JSArray pointsArray = new JSArray();
            if (points != null) {
                for (VDARGPSPoint point : points) {
                    pointsArray.put(PixliveHelper.gpsPointToJSObject(point));
                }
            }
            callback.success(new GetGPSPointsInBoundingBoxResult(pointsArray));
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void getNearbyBeacons(@NonNull NonEmptyResultCallback<GetNearbyBeaconsResult> callback) {
        try {
            List<String> beaconContextIds = VDARSDKController.getInstance().getNearbyBeacons();
            JSArray contextsArray = new JSArray();
            if (beaconContextIds != null) {
                for (String contextId : beaconContextIds) {
                    VDARContext context = VDARSDKController.getInstance().getContext(contextId);
                    if (context != null) {
                        contextsArray.put(PixliveHelper.contextToJSObject(context));
                    }
                }
            }
            callback.success(new GetNearbyBeaconsResult(contextsArray));
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void startNearbyGPSDetection(@NonNull EmptyCallback callback) {
        try {
            VDARSDKController.getInstance().startNearbyGPSDetection();
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void stopNearbyGPSDetection(@NonNull EmptyCallback callback) {
        try {
            VDARSDKController.getInstance().stopNearbyGPSDetection();
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void startGPSNotifications(@NonNull EmptyCallback callback) {
        try {
            VDARSDKController.getInstance().startGPSNotifications();
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void stopGPSNotifications(@NonNull EmptyCallback callback) {
        try {
            VDARSDKController.getInstance().stopGPSNotifications();
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void setNotificationsSupport(@NonNull SetNotificationsSupportOptions options, @NonNull EmptyCallback callback) {
        try {
            VDARSDKController.getInstance().setNotificationsSupport(options.isEnabled());
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void setInterfaceLanguage(@NonNull SetInterfaceLanguageOptions options, @NonNull EmptyCallback callback) {
        try {
            VDARSDKController.getInstance().forceLanguage(options.getLanguage());
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void createARView(@NonNull CreateARViewOptions options, @NonNull EmptyCallback callback) {
        if (annotationView != null) {
            callback.error(CustomExceptions.AR_VIEW_ALREADY_EXISTS);
            return;
        }
        Activity activity = plugin.getActivity();
        activity.runOnUiThread(() -> {
            try {
                WebView webView = plugin.getBridge().getWebView();
                annotationView = new VDARAnnotationView(activity);
                float density = activity.getResources().getDisplayMetrics().density;
                int x = (int) (options.getX() * density);
                int y = (int) (options.getY() * density);
                int width = (int) (options.getWidth() * density);
                int height = (int) (options.getHeight() * density);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
                params.leftMargin = x;
                params.topMargin = y;
                annotationView.setLayoutParams(params);
                webView.setBackgroundColor(Color.TRANSPARENT);

                ViewGroup parent = (ViewGroup) webView.getParent();
                int index = parent.indexOfChild(webView);
                ViewGroup.LayoutParams webViewParams = webView.getLayoutParams();
                parent.removeView(webView);

                TouchInterceptorView interceptor = new TouchInterceptorView(activity);
                interceptor.setLayoutParams(webViewParams);
                parent.addView(interceptor, index);

                webView.setLayoutParams(
                    new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                );
                interceptor.addView(webView);
                interceptor.addView(annotationView, 0);
                interceptor.setAnnotationView(annotationView);
                touchInterceptorView = interceptor;

                VDARSDKController.getInstance().setActivity(activity);
                annotationView.onResume();
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void destroyARView(@NonNull EmptyCallback callback) {
        if (annotationView == null) {
            callback.error(CustomExceptions.AR_VIEW_NOT_FOUND);
            return;
        }
        Activity activity = plugin.getActivity();
        activity.runOnUiThread(() -> {
            try {
                WebView webView = plugin.getBridge().getWebView();
                webView.setBackgroundColor(Color.WHITE);
                if (currentContext != null) {
                    currentContext.stop();
                    currentContext = null;
                }
                annotationView.onPause();

                if (touchInterceptorView != null) {
                    ViewGroup parent = (ViewGroup) touchInterceptorView.getParent();
                    int index = parent.indexOfChild(touchInterceptorView);
                    ViewGroup.LayoutParams interceptorParams = touchInterceptorView.getLayoutParams();

                    touchInterceptorView.removeView(annotationView);
                    touchInterceptorView.removeView(webView);
                    parent.removeView(touchInterceptorView);

                    webView.setLayoutParams(interceptorParams);
                    parent.addView(webView, index);
                    touchInterceptorView = null;
                }

                annotationView = null;
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void resizeARView(@NonNull ResizeARViewOptions options, @NonNull EmptyCallback callback) {
        if (annotationView == null) {
            callback.error(CustomExceptions.AR_VIEW_NOT_FOUND);
            return;
        }
        Activity activity = plugin.getActivity();
        activity.runOnUiThread(() -> {
            try {
                float density = activity.getResources().getDisplayMetrics().density;
                int x = (int) (options.getX() * density);
                int y = (int) (options.getY() * density);
                int width = (int) (options.getWidth() * density);
                int height = (int) (options.getHeight() * density);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
                params.leftMargin = x;
                params.topMargin = y;
                annotationView.setLayoutParams(params);
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void setARViewTouchEnabled(@NonNull SetARViewTouchEnabledOptions options, @NonNull EmptyCallback callback) {
        if (touchInterceptorView != null) {
            plugin
                .getActivity()
                .runOnUiThread(() -> {
                    if (touchInterceptorView != null) {
                        touchInterceptorView.setTouchEnabled(options.isEnabled());
                    }
                });
        }
        callback.success();
    }

    public void setARViewTouchHole(@NonNull SetARViewTouchHoleOptions options, @NonNull EmptyCallback callback) {
        float density = plugin.getActivity().getResources().getDisplayMetrics().density;
        float top = (float) (options.getTop() * density);
        float bottom = (float) (options.getBottom() * density);
        float left = (float) (options.getLeft() * density);
        float right = (float) (options.getRight() * density);
        if (touchInterceptorView != null) {
            plugin
                .getActivity()
                .runOnUiThread(() -> {
                    if (touchInterceptorView != null) {
                        touchInterceptorView.setTouchHole(top, bottom, left, right);
                    }
                });
        }
        callback.success();
    }

    // VDARSDKControllerEventReceiver

    @Override
    public void onCodesRecognized(@NonNull ArrayList<VDARCode> codes) {
        for (VDARCode code : codes) {
            if (code.isSpecialCode()) {
                continue;
            }
            JSObject data = new JSObject();
            data.put("code", code.getCodeData());
            data.put("type", PixliveHelper.codeTypeToString(code.getCodeType()));
            plugin.notifyListenersFromImplementation("codeRecognize", data);
        }
    }

    @Override
    public void onFatalError(@NonNull String error) {
        // Not needed
    }

    @Override
    public void onPresentAnnotations() {
        plugin.notifyListenersFromImplementation("presentAnnotations", new JSObject());
    }

    @Override
    public void onAnnotationsHidden() {
        plugin.notifyListenersFromImplementation("hideAnnotations", new JSObject());
    }

    @Override
    public void onTrackingStarted(int width, int height) {
        // Not needed
    }

    @Override
    public void onEnterContext(@NonNull VDARContext context) {
        this.currentContext = context;
        JSObject data = new JSObject();
        data.put("contextId", context.getRemoteID());
        plugin.notifyListenersFromImplementation("enterContext", data);
    }

    @Override
    public void onExitContext(@NonNull VDARContext context) {
        this.currentContext = null;
        JSObject data = new JSObject();
        data.put("contextId", context.getRemoteID());
        plugin.notifyListenersFromImplementation("exitContext", data);
    }

    @Override
    public void onRequireSynchronization(@NonNull ArrayList<VDARPrior> priors) {
        JSArray tagsArray = new JSArray();
        for (VDARPrior prior : priors) {
            if (prior instanceof VDARTagPrior) {
                tagsArray.put(((VDARTagPrior) prior).getTag());
            }
        }
        JSObject data = new JSObject();
        data.put("tags", tagsArray);
        plugin.notifyListenersFromImplementation("requireSync", data);
    }

    // VDARContentEventReceiver

    @Override
    public void onReceiveContentEvent(@NonNull String eventName, @NonNull String eventParams) {
        JSObject data = new JSObject();
        data.put("name", eventName);
        data.put("params", eventParams);
        plugin.notifyListenersFromImplementation("eventFromContent", data);
    }

    // VDARRemoteControllerListener

    @Override
    public void onSyncProgress(@NonNull VDARRemoteController controller, float progress, boolean isDone, @Nullable String error) {
        JSObject data = new JSObject();
        data.put("progress", progress / 100.0f);
        plugin.notifyListenersFromImplementation("syncProgress", data);
    }
}
