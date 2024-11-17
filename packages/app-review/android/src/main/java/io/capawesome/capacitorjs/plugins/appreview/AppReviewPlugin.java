package io.capawesome.capacitorjs.plugins.appreview;

import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.android.play.core.review.ReviewInfo;

@CapacitorPlugin(name = "AppReview")
public class AppReviewPlugin extends Plugin {

    private AppReview appReview;
    private ReviewInfo reviewInfo;

    @Override
    public void load() {
        appReview = new AppReview(getActivity());
    }

    @PluginMethod
    public void openAppStore(PluginCall call) {
        if (reviewInfo != null) {
            appReview.launchReviewFlow(
                reviewInfo,
                new ReviewResultCallback<Void>() {
                    @Override
                    public void success(Void result) {
                        call.resolve();
                    }

                    @Override
                    public void error(Exception exception) {
                        String message = exception.getMessage();
                        Logger.error(message, exception);
                        call.reject(message);
                    }
                }
            );
        } else {
            call.reject("ReviewInfo is not available. Please call requestReview first.");
        }
    }

    @PluginMethod
    public void requestReview(PluginCall call) {
        appReview.requestReviewFlow(
            new ReviewResultCallback<ReviewInfo>() {
                @Override
                public void success(ReviewInfo info) {
                    reviewInfo = info;
                    JSObject result = new JSObject();
                    result.put("canLaunchReviewFlow", true);
                    call.resolve(result);
                }

                @Override
                public void error(Exception exception) {
                    String message = exception.getMessage();
                    Logger.error(message, exception);
                    call.reject(message);
                }
            }
        );
    }
}
