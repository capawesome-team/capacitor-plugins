package io.capawesome.capacitorjs.plugins.appreview;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import io.capawesome.capacitorjs.plugins.appreview.interfaces.EmptyCallback;

public class AppReview {

    @NonNull
    private final AppReviewPlugin plugin;

    public AppReview(@NonNull AppReviewPlugin plugin) {
        this.plugin = plugin;
    }

    public void openAppStore(@Nullable String storePackageName) {
        String packageName = plugin.getContext().getPackageName();
        Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
        if (storePackageName != null && tryStartActivity(new Intent(marketIntent).setPackage(storePackageName))) {
            return;
        }
        if (tryStartActivity(marketIntent)) {
            return;
        }
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
        plugin.getBridge().getActivity().startActivity(webIntent);
    }

    public void requestReviewFlow(EmptyCallback callback) {
        final ReviewManager manager = ReviewManagerFactory.create(plugin.getActivity());
        manager.requestReviewFlow().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ReviewInfo reviewInfo = task.getResult();
                manager.launchReviewFlow(plugin.getActivity(), reviewInfo).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        callback.success();
                    } else {
                        callback.error(task1.getException());
                    }
                });
            } else {
                callback.error(task.getException());
            }
        });
    }

    private boolean tryStartActivity(@NonNull Intent intent) {
        try {
            plugin.getBridge().getActivity().startActivity(intent);
            return true;
        } catch (ActivityNotFoundException exception) {
            return false;
        }
    }
}
