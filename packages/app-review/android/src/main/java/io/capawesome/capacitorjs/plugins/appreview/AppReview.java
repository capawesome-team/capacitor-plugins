package io.capawesome.capacitorjs.plugins.appreview;

import android.app.Activity;
import android.util.Log;
import com.getcapacitor.Logger;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;

public class AppReview {

    private final ReviewManager manager;
    private final Activity activity;

    public AppReview(Activity activity) {
        this.activity = activity;
        this.manager = ReviewManagerFactory.create(activity);
    }

    public void requestReviewFlow(ReviewResultCallback<ReviewInfo> callback) {
        manager
            .requestReviewFlow()
            .addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        ReviewInfo reviewInfo = task.getResult();
                        Logger.info("AppReview", "ReviewInfo retrieved successfully.");
                        callback.success(reviewInfo);
                    } else {
                        String error = "Failed to retrieve ReviewInfo.";
                        Logger.error(error, task.getException());
                        callback.error(task.getException());
                    }
                }
            );
    }

    public void launchReviewFlow(ReviewInfo reviewInfo, ReviewResultCallback<Void> callback) {
        Task<Void> flow = manager.launchReviewFlow(activity, reviewInfo);
        flow.addOnSuccessListener(
            result -> {
                Logger.info("AppReview", "Review flow launched successfully.");
                callback.success(null);
            }
        );
        flow.addOnFailureListener(
            exception -> {
                String error = "Failed to launch review flow.";
                Logger.error(error, exception);
                callback.error(exception);
            }
        );
    }
}
