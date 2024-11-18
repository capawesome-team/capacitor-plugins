package io.capawesome.capacitorjs.plugins.appreview;

import androidx.appcompat.app.AppCompatActivity;
import com.getcapacitor.Logger;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;

public class AppReview {

    public AppReview(AppReviewPlugin plugin) {}

    public void requestReviewFlow(AppCompatActivity activity, EmptyCallback callback) {
        final ReviewManager manager = ReviewManagerFactory.create(activity.getApplicationContext());
        manager
            .requestReviewFlow()
            .addOnCompleteListener(
                task -> {
                    if (!task.isSuccessful()) {
                        String error = "Failed to retrieve ReviewInfo.";
                        Logger.error("AppReview", error, task.getException());
                        callback.error(new Exception(error, task.getException()));
                        return;
                    }

                    ReviewInfo reviewInfo = task.getResult();
                    Logger.info("AppReview", "Review info retrieved successfully.");

                    manager
                        .launchReviewFlow(activity, reviewInfo)
                        .addOnCompleteListener(
                            task1 -> {
                                if (task1.isSuccessful()) {
                                    Logger.info("AppReview", "Review flow completed successfully.");
                                    callback.success();
                                }
                            }
                        )
                        .addOnFailureListener(
                            exception -> {
                                String error = "Unexpected error during review flow launch.";
                                Logger.error("AppReview", error, exception);
                                callback.error(new Exception(error, exception));
                            }
                        );
                }
            )
            .addOnFailureListener(
                exception -> {
                    String error = "Unexpected error retrieving ReviewInfo.";
                    Logger.error("AppReview", error, exception);
                    callback.error(new Exception(error, exception));
                }
            );
    }
}
