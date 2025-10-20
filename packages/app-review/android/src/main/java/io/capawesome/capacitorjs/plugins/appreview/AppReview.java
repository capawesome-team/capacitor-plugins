package io.capawesome.capacitorjs.plugins.appreview;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
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

    public void openAppStore() {
        String packageName = plugin.getContext().getPackageName();
        Intent launchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));

        try {
            plugin.getBridge().getActivity().startActivity(launchIntent);
        } catch (ActivityNotFoundException ex) {
            launchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
            plugin.getBridge().getActivity().startActivity(launchIntent);
        }
    }

    public void requestReviewFlow(EmptyCallback callback) {
        final ReviewManager manager = ReviewManagerFactory.create(plugin.getActivity());
        manager
            .requestReviewFlow()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    ReviewInfo reviewInfo = task.getResult();
                    manager
                        .launchReviewFlow(plugin.getActivity(), reviewInfo)
                        .addOnCompleteListener(task1 -> {
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
}
