package io.capawesome.capacitorjs.plugins.appreview;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "AppReview")
public class AppReviewPlugin extends Plugin {

    private AppReview appReview;

    @Override
    public void load() {
        appReview = new AppReview(this);
    }

    @PluginMethod
    public void openAppStore(PluginCall call) {
        try {
            String packageName = this.getContext().getPackageName();
            Intent launchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));

            try {
                this.getBridge().getActivity().startActivity(launchIntent);
            } catch (ActivityNotFoundException ex) {
                launchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
                this.getBridge().getActivity().startActivity(launchIntent);
            }
            call.resolve();
        } catch (Exception exception) {
            Logger.error(exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void requestReview(PluginCall call) {
        final AppCompatActivity activity = getActivity();
        appReview.requestReviewFlow(
            activity,
            new EmptyCallback() {
                @Override
                public void success() {
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
